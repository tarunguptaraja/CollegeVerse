package com.tarunguptaraja.collegeverse.views

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.tarunguptaraja.collegeverse.databinding.ActivityPendingBinding
import com.tarunguptaraja.collegeverse.model.Student
import com.tarunguptaraja.collegeverse.model.Teacher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class PendingActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityPendingBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var gson: Gson
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityPendingBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        sharedPreferences=applicationContext.getSharedPreferences("MySharedPref", MODE_PRIVATE)
        editor = sharedPreferences.edit()
        gson = Gson()

        val userId = Firebase.auth.currentUser?.uid
        runBlocking { getUserDoc(userId.toString()) }

    }

    private suspend fun getUserDoc(userId: String): DocumentSnapshot {

        val user = db.collection("users").document(userId).get()
        user.await()
        val userDoc = user.result
        if (userDoc.exists()) {
            val status = userDoc.data!!["status"]
            if (status == "CONFIRMED") {
                val role=userDoc.data!!["role"]
                if(role=="Student"){
                    val studentId = userDoc.data!!["studentId"].toString()
                    runBlocking { loadStudent(studentId) }
                }else{
                    val facultyId:String = userDoc.data!!["facultyId"].toString()
                    runBlocking { loadTeacher(facultyId) }
                }
                editor.apply()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }else if(status == "CANCELLED"){
                startActivity(Intent(this, GetUserDetails::class.java))
                finish()
            }
        } else {
            startActivity(Intent(this, GetUserDetails::class.java))
            finish()
        }
        return user.result
    }
    private suspend fun loadStudent(studentId:String){
        val student = db.collection("students").document(studentId).get()
        student.await()
        val studentDoc = student.result.toObject(Student::class.java)
        val json = gson.toJson(studentDoc)
        editor.putString("STUDENT", json)
        editor.apply()
    }
    private suspend fun loadTeacher(facultyId:String){
        val faculty = db.collection("teachers").document(facultyId).get()
        faculty.await()
        val facultyDoc = faculty.result.toObject(Teacher::class.java)
        val json = gson.toJson(facultyDoc)
        editor.putString("TEACHER", json)
        editor.apply()
    }
}