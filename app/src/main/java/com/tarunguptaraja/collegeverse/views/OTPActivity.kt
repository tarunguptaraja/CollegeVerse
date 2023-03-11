package com.tarunguptaraja.collegeverse.views

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.core.text.HtmlCompat
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.tarunguptaraja.collegeverse.R
import com.tarunguptaraja.collegeverse.databinding.ActivityOtpactivityBinding
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityOtpactivityBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var storedVerificationId: String
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var phoneNumber = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        supportActionBar?.hide()

        viewBinding.toolbar.flBack.setOnClickListener {
            finish()
        }

        val sharedPreferences: SharedPreferences =
            applicationContext.getSharedPreferences("MySharedPref", MODE_PRIVATE)
        auth = FirebaseAuth.getInstance()

        storedVerificationId = intent.getStringExtra("storedVerificationId").toString()
        phoneNumber = intent.getStringExtra("phoneNumber").toString()


        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, GetUserDetails::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }

        }

        viewBinding.toolbar.tvActivityTitle.text = "Sign In/Sign Up"

        val termAndCondition =
            ("<font color=" + Color.BLACK + ">By continuing you agree to </font><font color=" + R.color.primary + ">Terms of Services and Privacy and Legal Policy.</font>")
        viewBinding.terms.text = Html.fromHtml(termAndCondition, HtmlCompat.FROM_HTML_MODE_LEGACY)

        viewBinding.textviewMobile.text = phoneNumber

        viewBinding.buttonVerify.setOnClickListener {
            val otp = viewBinding.edtOtp.text.trim().toString()
            if (otp.isNotEmpty()) {
                val credential: PhoneAuthCredential =
                    PhoneAuthProvider.getCredential(storedVerificationId, otp)
                auth.signInWithCredential(credential).addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putBoolean("hasLoggedIn", true)
                        editor.putString("phoneNumber",phoneNumber)
                        editor.apply()
                        startActivity(Intent(applicationContext, GetUserDetails::class.java))
                        finish()
                    } else {
                        if (it.exception is FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        viewBinding.resend.setOnClickListener {
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }
}