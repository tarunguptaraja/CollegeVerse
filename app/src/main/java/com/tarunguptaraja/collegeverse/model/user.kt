package com.tarunguptaraja.collegeverse.model

import com.google.firebase.Timestamp
import java.io.Serializable
import java.util.*


data class User(
    val userId: String,
    val name: String,
    val phoneNumber: String,
    val role: String,
    var studentId: String?,
    var facultyId: String?,
    val father: String,
    val mother: String,
    val email: String,
    val aadharNumber: Long,
    val gender: String,
    val dob: Date,
    val course: String = "B.tech",
    val branch: String,
    val admissionYear: Int,
    val admissionSemester: Int?,
    val addressLine1: String,
    val addressLine2: String?,
    val city: String,
    val district: String,
    val pincode: Int,
    val state: String,
    var status: String,
    val timestamp: Timestamp
) : Serializable {
    constructor() : this(
        "",
        "",
        "",
        "",
        null,
        null,
        "",
        "",
        "",
        0,
        "",
        Calendar.getInstance().time,
        "",
        "",
        0,
        0,
        "",
        null,
        "",
        "",
        0,
        "",
        "",
        Timestamp.now()
    )
}