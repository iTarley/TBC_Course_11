package com.example.tbc_course_11.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tbc_course_11.UserActivity
import com.example.tbc_course_11.databinding.CardLayoutBinding
import com.example.tbc_course_11.models.User
import com.google.firebase.database.FirebaseDatabase

class CustomAdapter(private var dataSet: ArrayList<User>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private val database = FirebaseDatabase.getInstance().getReference("/User")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(CardLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = dataSet[position]
        holder.binding.apply {
            personName.text = currentItem.name
            personLastName.text = currentItem.lastName
            personEmail.text = currentItem.email
            imageButtonRemove.setOnClickListener {
                database.child("${currentItem.name} ${currentItem.lastName}").removeValue()

                dataSet.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, dataSet.size)
            }
            imageButtonEdit.setOnClickListener {
                val intent = Intent(it.context, UserActivity::class.java)
                intent.putExtra("name", currentItem.name)
                intent.putExtra("lastName", currentItem.lastName)
                intent.putExtra("email", currentItem.email)
                it.context.startActivity(intent)
            }
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

    inner class ViewHolder(val binding: CardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)


}