package com.example.tbc_course_11.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tbc_course_11.R
import com.example.tbc_course_11.models.User

class CustomAdapter (private val dataSet: ArrayList<User>):RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {

        val currentItem = dataSet[position]
        holder.name.text = currentItem.name
        holder.lastName.text = currentItem.lastName
        holder.email.text = currentItem.email
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: AppCompatTextView
        val lastName: AppCompatTextView
        val email: AppCompatTextView

        init {
            name = view.findViewById(R.id.personName)
            lastName = view.findViewById(R.id.personLastName)
            email = view.findViewById(R.id.personEmail)
        }
    }
}