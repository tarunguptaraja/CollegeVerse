package com.tarunguptaraja.collegeverse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tarunguptaraja.collegeverse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val PREF_NAME = "MySharedPref"
    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

//        viewBinding.
    }
}