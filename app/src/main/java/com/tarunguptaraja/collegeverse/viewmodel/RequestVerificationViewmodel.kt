package com.tarunguptaraja.collegeverse.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tarunguptaraja.collegeverse.model.Student
import com.tarunguptaraja.collegeverse.model.Teacher
import com.tarunguptaraja.collegeverse.model.User
import kotlinx.coroutines.tasks.await
import java.util.*

class RequestVerificationViewmodel : ViewModel() {
    lateinit var user: User
    private val db = Firebase.firestore

    suspend fun verifyStudentRequest(enrollment: Long, roll: Long) {

        val calender: Calendar = Calendar.getInstance()
        var currentYear: Int = calender.get(Calendar.YEAR) - user.admissionYear.toString().toInt()
        var currentSem: Int = (currentYear * 2) + user.admissionSemester.toString().toInt()
        if (calender.get(Calendar.MONTH) < 7) {
            currentSem -= 1
        }
        currentYear = (currentSem + 1) / 2

        val student = Student(
            null,
            user.name,
            roll,
            enrollment,
            user.phoneNumber,
            user.email,
            user.course,
            currentYear,
            user.branch,
            user.userId,
            user.role,
            user.admissionYear,
            currentSem,
            false,
            Timestamp.now()
        )
        db.collection("students").document().set(student).await()
        val studentDoc = db.collection("students").whereEqualTo("userId", user.userId).get()
        studentDoc.await()
        val studentId: String = studentDoc.result.documents[0].id
        student.studentId = studentId
        db.collection("students").document(studentId).set(student).await()
        user.studentId = studentId
        user.status = "CONFIRMED"
        db.collection("users").document(user.userId).set(user).await()

    }

    suspend fun verifyFacultyRequest() {
        val teacher = Teacher(
            user.userId,
            user.name,
            user.phoneNumber,
            null,
            user.email,
            user.course,
            user.branch,
            false,
            Timestamp.now()
        )
        db.collection("teachers").document().set(teacher).await()
        val teacherDoc = db.collection("teachers").whereEqualTo("userId", user.userId).get()
        teacherDoc.await()
        val facultyId: String = teacherDoc.result.documents[0].id
        teacher.facultyId = facultyId
        user.facultyId = facultyId
        user.status = "CONFIRMED"
        db.collection("teachers").document(facultyId).set(teacher).await()
        db.collection("users").document(user.userId).set(user).await()
    }

    suspend fun cancelRequest() {
        user.status = "CANCELLED"
        val requestedUser = db.collection("users").document(user.userId).set(user)
        requestedUser.await()
    }


}