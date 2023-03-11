package com.tarunguptaraja.collegeverse.model

import java.util.*

data class event(
    val name: String,
    val description: String?,
    val date: Date,
    val year: List<Int>,
    val branch: List<String>,
    val organizerName: String?
) {
    constructor() : this("", null, Calendar.getInstance().time, listOf(0), listOf(""), null)


}
