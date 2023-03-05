package com.tarunguptaraja.collegeverse.model

data class student(
    val studentId: String,
    val name: String,
    val roll: Long,
    val phoneNumber: String,
    val father: String,
    val mother: String,
    val course: String = "B.tech",
    val year: Int,
    val branch: String,
    val dob: String,
    val doa: String
)
