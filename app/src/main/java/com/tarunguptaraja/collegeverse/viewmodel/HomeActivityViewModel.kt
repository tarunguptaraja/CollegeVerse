package com.tarunguptaraja.collegeverse.viewmodel

import androidx.lifecycle.ViewModel
import com.tarunguptaraja.collegeverse.model.Student
import com.tarunguptaraja.collegeverse.model.Teacher
import com.tarunguptaraja.collegeverse.model.User

class HomeActivityViewModel: ViewModel() {
    lateinit var user: User
    lateinit var student:Student
    lateinit var teacher:Teacher
}