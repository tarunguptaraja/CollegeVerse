package com.tarunguptaraja.collegeverse.model

import com.google.firebase.Timestamp

data class Student(
    var studentId: String?,
    val name: String,
    val roll: Long,
    val enrollmentNumber: Long,
    val phoneNumber: String,
    val email: String,
    val course: String = "B.tech",
    val year: Int,
    val branch: String,
    val userId: String,
    val role: String,
    val admissionYear: Int,
    val currentSemester: Int,
    @field:JvmField val isCR: Boolean,
    val timestamp: Timestamp
) {
    constructor() : this("", "", 0, 0, "", "", "", 0, "", "", "", 0, 0, false, Timestamp.now())
}
