package com.tarunguptaraja.collegeverse.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tarunguptaraja.collegeverse.model.User
import com.tarunguptaraja.collegeverse.model.event
import kotlinx.coroutines.tasks.await
import java.util.Calendar

class EventViewmodel : ViewModel() {

    lateinit var user: User
    private val db = Firebase.firestore

    suspend fun getEvents(): List<event> {
        val eventList = ArrayList<event>()
        val date = Calendar.getInstance().time

        val eventDocs = db.collection("events").whereGreaterThanOrEqualTo("date", date)
            .whereArrayContains("branch", user.branch).get()
        eventDocs.await()
        val events = eventDocs.result.documents
        for (doc in events) {
            val event = doc.toObject(event::class.java)
            eventList.add(event!!)
        }
        Log.i("EventViewModel", events.size.toString())
        return eventList
    }
}