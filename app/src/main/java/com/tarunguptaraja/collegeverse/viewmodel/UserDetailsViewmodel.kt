package com.tarunguptaraja.collegeverse.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tarunguptaraja.collegeverse.model.User
import kotlinx.coroutines.tasks.await
import java.time.Year
import java.util.*

class UserDetailsViewmodel : ViewModel() {


    val calendar = Calendar.getInstance()

    var userId: String = ""
    var name: String = ""
    var phoneNumber: String = ""
    var role: String = ""
    var email: String = ""
    var aadharNumber: Long = 0
    var gender: String = "Male"
    var father: String = ""
    var mother: String = ""
    var course: String = "B.tech"
    var branch: String = ""
    var dob: Date
    var yoa: Int=0
    var soa: Int? = null
    var address_line1: String = ""
    var address_line2: String = ""
    var city: String = ""
    var state: String = ""
    var district: String = ""
    var pincode: Int = 0
    var timestamp: Timestamp = Timestamp.now()
    val db = Firebase.firestore
    private lateinit var user: User

    init {
        calendar.set(1997, 1, 1)
        dob = calendar.time
    }

    suspend fun uploadUser():User {
        val fuser = Firebase.auth.currentUser
        fuser?.let {
            Log.i("notupdated", it.uid)
            userId = it.uid
            phoneNumber = it.phoneNumber.toString()
        }
        timestamp = Timestamp.now()
        val muser = User(
            userId,
            name,
            phoneNumber,
            role,
            null,
            null,
            father,
            mother,
            email,
            aadharNumber,
            gender,
            dob,
            course,
            branch,
            yoa,
            soa,
            address_line1,
            address_line2,
            city,
            district,
            pincode,
            state,
            "PENDING",
            timestamp
        )

        val tt = db.collection("users").document(userId).set(muser).addOnSuccessListener {
            Log.i("notupdated", "success")
        }.addOnFailureListener {
            Log.i("notupdated", it.stackTrace.toString())
        }

        tt.await()
        return muser
    }

    suspend fun isRegistered(): Boolean {
        val userId = Firebase.auth.currentUser?.uid
        val userDoc = db.collection("users").document(userId!!).get()
        userDoc.await()
        val res:Boolean= userDoc.result.exists()
        if(res){
            user = userDoc.result.toObject(User::class.java)!!
        }
        return res
    }

    fun getUser(): User {
        return user
    }


}