package com.tarunguptaraja.collegeverse.model

import com.google.firebase.Timestamp

data class Teacher(
    val userId:String,
    val name: String,
    val phoneNumber: String,
    val facultyId:String,
    val email: String,
    val course: String = "B.tech",
    val branch: String,
    val timestamp: Timestamp
)
