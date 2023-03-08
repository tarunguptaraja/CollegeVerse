package com.tarunguptaraja.collegeverse.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tarunguptaraja.collegeverse.R
import com.tarunguptaraja.collegeverse.model.User

class RequestAdapter(private val requestList: List<User>, private val listner:RequestItemClicked) :
    RecyclerView.Adapter<RequestAdapter.Viewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_request, parent, false)
        val viewholder= Viewholder(view)
        view.setOnClickListener {
            listner.onItemClicked(requestList[viewholder.adapterPosition])
        }
        return viewholder
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = requestList[position]
        holder.role.text = item.role
        holder.username.text = item.name
    }

    override fun getItemCount(): Int {
        return requestList.size
    }

    class Viewholder(view: View) : RecyclerView.ViewHolder(view) {
        val username = view.findViewById<TextView>(R.id.username)
        val role = view.findViewById<TextView>(R.id.role)
    }

    interface RequestItemClicked{
        fun onItemClicked(item: User)
    }
}