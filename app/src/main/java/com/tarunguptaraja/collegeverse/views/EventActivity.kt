package com.tarunguptaraja.collegeverse.views

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.tarunguptaraja.collegeverse.adapters.EventAdapter
import com.tarunguptaraja.collegeverse.databinding.ActivityEventBinding
import com.tarunguptaraja.collegeverse.model.User
import com.tarunguptaraja.collegeverse.viewmodel.EventViewmodel
import kotlinx.coroutines.runBlocking

class EventActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityEventBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.toolbar.tvActivityTitle.text="Events/Exams"
        viewBinding.toolbar.flBack.setOnClickListener {
            finish()
        }
        val viewModel = EventViewmodel()
        val sharedPreferences: SharedPreferences =
            applicationContext.getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val gson = Gson()
        val json: String? = sharedPreferences.getString("USER", "")
        val user: User = gson.fromJson(json, User::class.java)
        viewModel.user = user

        viewBinding.recycleView.layoutManager = LinearLayoutManager(applicationContext)
        val data = runBlocking { viewModel.getEvents() }
        val adapter = EventAdapter(data)
        viewBinding.recycleView.adapter = adapter

    }
}