package com.tarunguptaraja.collegeverse.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.tarunguptaraja.collegeverse.MainActivity
import com.tarunguptaraja.collegeverse.databinding.ActivityRequestVerificationBinding
import com.tarunguptaraja.collegeverse.model.User
import com.tarunguptaraja.collegeverse.viewmodel.RequestVerificationViewmodel
import kotlinx.coroutines.runBlocking

class RequestVerificationActivity : AppCompatActivity() {

    private lateinit var viewBinding:ActivityRequestVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding= ActivityRequestVerificationBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.toolbar.btnBack.setOnClickListener{
            finish()
        }
        viewBinding.toolbar.tvActivityTitle.text="Pending Request"

        val viewModel= RequestVerificationViewmodel()
        val gson= Gson()
        val userRequest=intent.getStringExtra("userRequest")
        val user: User = gson.fromJson(userRequest, User::class.java)
        viewModel.user=user

        val viewableDate:String = user.dob.toString().substring(4,10)+", "+user.dob.toString().substring(30)

        if(user.role=="Teacher"){
            viewBinding.enrollmentNumber.visibility=View.GONE
            viewBinding.rollNumber.visibility=View.GONE
            viewBinding.line.visibility=View.GONE
        }

        viewBinding.name.setText("Name: ${user.name}")
        viewBinding.fatherName.setText("Father's Name: ${user.father}")
        viewBinding.motherName.setText("Mother's Name: ${user.mother}")
        viewBinding.aadhar.setText("Aadhaar Number: ${user.aadharNumber}")
        viewBinding.email.setText("Email: ${user.email}")
        viewBinding.textviewDob.text=("DOB: $viewableDate")
        viewBinding.textviewGender.text=("Gender: ${user.gender}")
        viewBinding.textviewBranch.text=("Branch: ${user.branch}")
        viewBinding.textviewRole.text=("Role: ${user.role}")
        viewBinding.textviewCourse.text=("Course: ${user.course}")
        viewBinding.textviewState.text=("State: ${user.state}")
        viewBinding.addressLine1.setText("Address Line 1: ${user.addressLine1}")
        viewBinding.addressLine2.setText("Address Line 2: ${user.addressLine2}")
        viewBinding.city.setText("City: ${user.city}")
        viewBinding.addmissionYear.setText("Admission Year: ${user.admissionYear}")
        viewBinding.district.setText("District: ${user.district}")
        viewBinding.pincode.setText("Pin code: ${user.pincode}")
        if(user.role=="Student"){
            viewBinding.addmissionSem.visibility= View.VISIBLE
            viewBinding.addmissionSem.setText("Admission Semester: ${user.admissionSemester}")
        }

        viewBinding.buttonVerify.setOnClickListener {

            if (user.role=="Student"){
                val roll=viewBinding.rollNumber.text
                val enrollment=viewBinding.enrollmentNumber.text
                if(roll.isEmpty()){
                    viewBinding.error.text="Roll number cannot be empty."
                    viewBinding.error.visibility=View.VISIBLE
                }else if(enrollment.isEmpty()){
                    viewBinding.error.text="Enrollment number cannot br empty."
                    viewBinding.error.visibility=View.VISIBLE
                }else{
                    runBlocking { viewModel.verifyStudentRequest(enrollment.toString().toLong(), roll.toString().toLong()) }
                    startActivity(Intent(this,HomeActivity::class.java))
                    finish()
                }
            }else{
                runBlocking { viewModel.verifyFacultyRequest() }
                startActivity(Intent(this,HomeActivity::class.java))
                finish()
            }

        }

        viewBinding.buttonCancel.setOnClickListener {
            runBlocking { viewModel.cancelRequest() }
            finish()
        }

    }
}