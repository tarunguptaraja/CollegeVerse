package com.tarunguptaraja.collegeverse.views

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.tarunguptaraja.collegeverse.R
import com.tarunguptaraja.collegeverse.databinding.ActivityGetUserDetailsBinding
import com.tarunguptaraja.collegeverse.viewmodel.UserDetailsViewmodel
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*


class GetUserDetails : AppCompatActivity() {

    private lateinit var viewBinding: ActivityGetUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityGetUserDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        supportActionBar?.hide()

        val viewModel = UserDetailsViewmodel()

        val temp = runBlocking { viewModel.isRegistered() }
        if (temp) {
            println("it's a returning user")
            startActivity(Intent(this, PendingActivity::class.java))
            finish()
        } else {
            println("it's a new user")
        }

        var isStudent = true

        val myCalendar: Calendar = Calendar.getInstance()

        val date = OnDateSetListener { view, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            val myFormat = "dd/MM/yy"
            val dateFormat = SimpleDateFormat(myFormat, Locale.US)
            viewBinding.textviewDob.text = dateFormat.format(myCalendar.time)
            viewModel.dob = myCalendar.time
        }

        viewBinding.spinnerGender.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedGender: String = parent?.getItemAtPosition(position).toString()

                    viewModel.role = selectedGender
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }


        viewBinding.spinnerRole.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedRole: String = parent?.getItemAtPosition(position).toString()

                    viewModel.role = selectedRole

                    if (selectedRole == "Student") {
                        viewBinding.spinnerBranch.adapter = ArrayAdapter(
                            this@GetUserDetails,
                            android.R.layout.simple_spinner_dropdown_item,
                            resources.getStringArray(R.array.items_branch)
                        )
                        isStudent = true
                        viewBinding.addmissionYear.hint = "Admission Year"
                        viewBinding.addmissionSem.visibility = View.VISIBLE
                    } else {
                        viewBinding.spinnerBranch.adapter = ArrayAdapter(
                            this@GetUserDetails,
                            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                            resources.getStringArray(
                                R.array.items_department
                            )
                        )
                        isStudent = false
                        viewBinding.addmissionYear.hint = "Joining Year"
                        viewBinding.addmissionSem.visibility = View.GONE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }

        viewBinding.spinnerCourse.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedCourse: String = parent?.getItemAtPosition(position).toString()
                    viewModel.course = selectedCourse
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }

        viewBinding.spinnerBranch.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedBranch: String = parent?.getItemAtPosition(position).toString()
                    viewModel.branch = selectedBranch
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }

        viewBinding.spinnerState.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedState: String = parent?.getItemAtPosition(position).toString()
                    viewModel.state = selectedState
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }

        viewBinding.dob.setOnClickListener {
            DatePickerDialog(
                this@GetUserDetails,
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }


        viewBinding.buttonVerify.setOnClickListener {
            viewBinding.error.visibility = View.GONE

            val name = viewBinding.name.text
            val father = viewBinding.fatherName.text
            val mother = viewBinding.motherName.text
            val email = viewBinding.email.text
            val aadhar = viewBinding.aadhar.text
            val year = viewBinding.addmissionYear.text
            val sem = viewBinding.addmissionSem.text
            val address1 = viewBinding.addressLine1.text
            val address2 = viewBinding.addressLine2.text
            val city = viewBinding.city.text
            val district = viewBinding.district.text
            val pincode = viewBinding.pincode.text
            var yoa: Year = Year.now()
            var iserror = false

            if (isStudent) {
                if (sem.isEmpty()) {
                    viewBinding.error.text = "Admission Semester cannot be empty"
                    viewBinding.error.visibility = View.VISIBLE
                    iserror=true
                } else if (sem.toString().toInt() % 2 == 0 || sem.toString().toInt() > 7) {
                    viewBinding.error.text = "Admission Semester should be 1, 3, 5 or 7"
                    viewBinding.error.visibility = View.VISIBLE
                    iserror=true
                } else {
                    viewModel.soa = sem.toString().toInt()
                }
            }
            if (year.isNotEmpty()) {
                try {
                    yoa = Year.of(year.toString().toInt())
                    if (year.toString().toInt()
                        < viewModel.dob.year
                    ) {
                        viewBinding.error.text =
                            "addmission year cannot be less then your dob cannot be empty"
                        viewBinding.error.visibility = View.VISIBLE
                        iserror=true
                    } else if (yoa > Year.now()) {
                        viewBinding.error.text =
                            "addmission year cannot be greater then current year cannot be empty"
                        viewBinding.error.visibility = View.VISIBLE
                        iserror=true
                    } else {
                        viewModel.yoa = yoa
                    }
                } catch (e: java.lang.Exception) {
                    viewBinding.error.text = "addmission year is not correct"
                    viewBinding.error.visibility = View.VISIBLE
                    iserror=true
                }
            }
            if (name.isEmpty()) {
                viewBinding.error.text = "Name cannot be empty"
                viewBinding.error.visibility = View.VISIBLE
            } else if (father.isEmpty()) {
                viewBinding.error.text = "Father's name cannot be empty"
                viewBinding.error.visibility = View.VISIBLE
            } else if (mother.isEmpty()) {
                viewBinding.error.text = "Mother's name cannot be empty"
                viewBinding.error.visibility = View.VISIBLE
            } else if (email.isEmpty()) {
                viewBinding.error.text = "Email cannot be empty"
                viewBinding.error.visibility = View.VISIBLE
            } else if (aadhar.isEmpty()||aadhar.length!=12) {
                viewBinding.error.text = "Aadhar Number length should be 12"
                viewBinding.error.visibility = View.VISIBLE
            } else if (viewBinding.textviewDob.text.isEmpty()) {
                viewBinding.error.text = "DOB cannot be empty"
                viewBinding.error.visibility = View.VISIBLE
            } else if (year.isEmpty()) {
                viewBinding.error.text = "Addmission Year cannot be empty"
                viewBinding.error.visibility = View.VISIBLE
            } else if (address1.isEmpty()) {
                viewBinding.error.text = "Address line 1 cannot be empty"
                viewBinding.error.visibility = View.VISIBLE
            } else if (city.isEmpty()) {
                viewBinding.error.text = "City name cannot be empty"
                viewBinding.error.visibility = View.VISIBLE
            } else if (district.isEmpty()) {
                viewBinding.error.text = "District cannot be empty"
                viewBinding.error.visibility = View.VISIBLE
            } else if (pincode.isEmpty()||pincode.length!=6) {
                viewBinding.error.text = "Pincode length should be 6"
                viewBinding.error.visibility = View.VISIBLE
            } else if(!iserror) {
                viewModel.name = name.toString()
                viewModel.father = father.toString()
                viewModel.mother = mother.toString()
                viewModel.email = email.toString()
                viewModel.aadharNumber = aadhar.toString().toLong()
                viewModel.address_line1 = address1.toString()
                viewModel.address_line2 = address2.toString()
                viewModel.city = city.toString()
                viewModel.district = district.toString()
                viewModel.pincode = pincode.toString().toInt()
                runBlocking { viewModel.uploadUser() }
                startActivity(Intent(applicationContext, PendingActivity::class.java))
                finish()
            }
        }


    }
}




