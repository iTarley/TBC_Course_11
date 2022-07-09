package com.example.tbc_course_11.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tbc_course_11.R
import com.example.tbc_course_11.UserActivity
import com.example.tbc_course_11.models.User
import com.google.firebase.database.FirebaseDatabase

class CustomAdapter(private var dataSet: ArrayList<User>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private val database = FirebaseDatabase.getInstance().getReference("/User")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = dataSet[position]
        holder.name.text = currentItem.name
        holder.lastName.text = currentItem.lastName
        holder.email.text = currentItem.email
        holder.removeBtn.setOnClickListener {
            database.child("${currentItem.name} ${currentItem.lastName}").removeValue()

            dataSet.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, dataSet.size)


        }
        holder.editBtn.setOnClickListener {
            val intent = Intent(it.context, UserActivity::class.java)
            intent.putExtra("name", currentItem.name)
            intent.putExtra("lastName", currentItem.lastName)
            intent.putExtra("email", currentItem.email)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

//    fun setData(newUserList: List<User>){
//        val diffUtil = MyDiffUtil(dataSet,newUserList)
//        val diffResults = DiffUtil.calculateDiff(diffUtil)
//        dataSet = newUserList
//        diffResults.dispatchUpdatesTo(this)
//    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: AppCompatTextView
        val lastName: AppCompatTextView
        val email: AppCompatTextView
        val removeBtn: ImageButton
        val editBtn: ImageButton

        init {
            name = view.findViewById(R.id.personName)
            lastName = view.findViewById(R.id.personLastName)
            email = view.findViewById(R.id.personEmail)
            removeBtn = view.findViewById(R.id.imageButtonRemove)
            editBtn = view.findViewById(R.id.imageButtonEdit)
        }
    }


}