package com.tarunguptaraja.collegeverse.views.ui.request

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tarunguptaraja.collegeverse.model.User
import kotlinx.coroutines.tasks.await

class RequestViewModel : ViewModel() {

    lateinit var user: User
    private val db = Firebase.firestore

    suspend fun getRequests(): ArrayList<User> {
        val arrayList = ArrayList<User>()
        val data = db.collection("users").whereEqualTo("status","PENDING").whereEqualTo("branch",user.branch).get()
        data.await()
        val res: MutableList<DocumentSnapshot> = data.result.documents
        for(doc in res){
            val user: User? = doc.toObject(User::class.java)
            arrayList.add(user!!)
        }
        return arrayList
    }
}