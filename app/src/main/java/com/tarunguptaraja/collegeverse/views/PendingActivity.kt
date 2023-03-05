package com.tarunguptaraja.collegeverse.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tarunguptaraja.collegeverse.databinding.ActivityPendingBinding
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class PendingActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityPendingBinding
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityPendingBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val userId = Firebase.auth.currentUser?.uid
        runBlocking { getUserDoc(userId.toString()) }

    }

    private suspend fun getUserDoc(userId: String): DocumentSnapshot {
        val user = db.collection("users").document(userId).get()
        user.await()
        val userDoc = user.result
        if (userDoc.exists()) {
            val status = userDoc.data!!["status"]
            if (status == "CONFIRMED") {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        } else {
            startActivity(Intent(this, GetUserDetails::class.java))
            finish()
        }
        return user.result
    }
}