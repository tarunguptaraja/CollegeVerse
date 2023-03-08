package com.tarunguptaraja.collegeverse.model

data class timetable(
    val start: Int,
    val duration: Int,
    val facultyId: String,
    val branch: String,
    val year: Int,
    val semester: Int,
    val subject: String,
    val day: String,
    val subjectCode: String,
    val facultyName: String
) {
    constructor() : this(0, 0, "", "", 0, 0, "", "", "", "")
}
