package com.tarunguptaraja.collegeverse.views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.tarunguptaraja.collegeverse.R
import com.tarunguptaraja.collegeverse.databinding.ActivityLoginBinding
import java.util.concurrent.TimeUnit


class LoginActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var phoneNumber = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        viewBinding.toolbar.tvActivityTitle.text = "Sign In/Sign Up"
        viewBinding.toolbar.flBack.setOnClickListener {
            finish()
        }

        val termAndCondition =
            ("<font color=" + Color.BLACK + ">By continuing you agree to </font><font color=" + R.color.primary + ">Terms of Services and Privacy and Legal Policy.</font>")
        viewBinding.terms.text = Html.fromHtml(termAndCondition, HtmlCompat.FROM_HTML_MODE_LEGACY)


        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, GetUserDetails::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(applicationContext, "$e", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String, token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
                resendToken = token
                val intent = Intent(applicationContext, OTPActivity::class.java)
                intent.putExtra("storedVerificationId", storedVerificationId)
                intent.putExtra("phoneNumber", phoneNumber)
                startActivity(intent)
                finish()
            }

        }

        viewBinding.edtPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewBinding.count.text = s.length.toString() + "/10"
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        viewBinding.buttonVerify.setOnClickListener {
            phoneNumber = "+91" + viewBinding.edtPhoneNumber.text.toString()
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