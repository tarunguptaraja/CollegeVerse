package com.tarunguptaraja.collegeverse.views

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.tarunguptaraja.collegeverse.databinding.ActivitySplashScreenBinding


class SplashScreen : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        FirebaseApp.initializeApp(applicationContext)

        val sharedPreferences: SharedPreferences = applicationContext.getSharedPreferences(
            "MySharedPref",
            MODE_PRIVATE
        )

        val hasLoggedIn: Boolean = sharedPreferences.getBoolean("hasLoggedIn", false)


        val intent: Intent = if (!hasLoggedIn) {
            Intent(this, LoginActivity::class.java)
        } else {
            Intent(this, GetUserDetails::class.java)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        }, 3000)
    }
}




