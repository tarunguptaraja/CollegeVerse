package com.tarunguptaraja.collegeverse.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tarunguptaraja.collegeverse.R
import com.tarunguptaraja.collegeverse.model.event
import java.text.SimpleDateFormat


class EventAdapter(private var list: List<event>) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventName: TextView = view.findViewById(R.id.event)
        val date: TextView = view.findViewById(R.id.date)
        val time: TextView = view.findViewById(R.id.time)
        val organizerName: TextView = view.findViewById(R.id.organizerName)
        val description: TextView = view.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
        val timeFormatter = SimpleDateFormat("hh:mm a")
        val item = list[position]
        holder.eventName.text = item.name
        holder.date.text = dateFormatter.format(item.date)
        holder.time.text = timeFormatter.format(item.date)
        if (item.description == null) {
            holder.description.visibility = View.GONE
        } else {
            holder.description.text = item.description
        }
        if (item.organizerName == null) {
            holder.organizerName.visibility = View.GONE
        } else {
            holder.organizerName.text = item.organizerName
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}