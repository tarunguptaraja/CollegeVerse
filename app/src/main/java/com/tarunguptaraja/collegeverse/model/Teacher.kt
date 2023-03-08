package com.tarunguptaraja.collegeverse.model

import com.google.firebase.Timestamp

data class Teacher(
    val userId: String,
    val name: String,
    val phoneNumber: String,
    var facultyId: String?,
    val email: String,
    val course: String = "B.tech",
    val branch: String,
    @field:JvmField
    val isHOD: Boolean,
    val timestamp: Timestamp
) {
    constructor() : this("", "", "", "", "", "", "",false, Timestamp.now())
}
