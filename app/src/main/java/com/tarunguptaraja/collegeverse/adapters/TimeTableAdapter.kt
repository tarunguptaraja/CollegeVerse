package com.tarunguptaraja.collegeverse.adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.tarunguptaraja.collegeverse.R
import com.tarunguptaraja.collegeverse.model.User
import com.tarunguptaraja.collegeverse.model.timetable

class TimeTableAdapter(private val mList: List<timetable>): RecyclerView.Adapter<TimeTableAdapter.ViewHolder>() {

    private lateinit var user:User


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_timetable, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        val start = item.start
        val end = start + item.duration

        holder.subjectView.text=item.subject
        holder.codeView.text=item.subjectCode
        holder.timeView.text = timeFormat(start)+" - "+timeFormat(end)
        if(holder.user.role=="Student")
            holder.teacherView.text=item.facultyName
        else
            holder.teacherView.text=item.year.toString()+" "+item.branch

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    private fun timeFormat(time:Int): String{
        return if(time>12){
            val temp=time-12
            if(temp<10){
                "0$temp:00 PM"
            }else{
                "$temp:00 PM"
            }
        }else{
            if(time<10){
                "0$time:00 AM"
            }else{
                "$time:00 AM"
            }
        }
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val sharedPreferences: SharedPreferences = view.context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val gson = Gson()
        val json: String? = sharedPreferences.getString("USER", "")
        val user: User = gson.fromJson(json, User::class.java)
        val subjectView = view.findViewById<TextView>(R.id.subject)
        val timeView = view.findViewById<TextView>(R.id.time)
        val codeView = view.findViewById<TextView>(R.id.subjectCode)
        val teacherView = view.findViewById<TextView>(R.id.teacher)
    }
}