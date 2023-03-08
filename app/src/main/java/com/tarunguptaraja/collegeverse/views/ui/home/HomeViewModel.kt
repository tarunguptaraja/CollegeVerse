package com.tarunguptaraja.collegeverse.views.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tarunguptaraja.collegeverse.model.User
import com.tarunguptaraja.collegeverse.model.timetable
import kotlinx.coroutines.tasks.await
import java.util.Calendar

class HomeViewModel : ViewModel() {

    private val arrayList = ArrayList<timetable>()
    private val db = Firebase.firestore
    lateinit var user: User
    private val days =
        arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

    suspend fun getTimetable(): ArrayList<timetable> {
        val calender: Calendar = Calendar.getInstance()
        val day = calender.get(Calendar.DAY_OF_WEEK)-1
        var year: Int = calender.get(Calendar.YEAR) - user.admissionYear.toString().toInt()
        var currentSem:Int=(year*2)+user.admissionSemester.toString().toInt()
        if (calender.get(Calendar.MONTH) < 7) {
            currentSem -= 1
        }
        year= (currentSem+1)/2
        arrayList.clear()
        val temp: Task<QuerySnapshot> = if(user.role=="Student"){
            db.collection("timetable").whereEqualTo("day", days[day]).whereEqualTo("year", year)
                .whereEqualTo("branch", user.branch).get()
        }else{
            db.collection("timetable").whereEqualTo("day",days[day]).whereEqualTo("facultyId",user.facultyId).get()
        }
        temp.await()
        val res: MutableList<DocumentSnapshot> = temp.result.documents
        for (doc in res) {
            val timetable: timetable? = doc.toObject(timetable::class.java)
            arrayList.add(timetable!!)
        }
        Log.i("notupdated", "$day $year")
        return arrayList
    }
}