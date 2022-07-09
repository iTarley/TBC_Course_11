package com.example.tbc_course_11.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.tbc_course_11.models.User

class MyDiffUtil(
    private val oldList: List<User>,
    private val newList: List<User>
) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].email == newList[newItemPosition].email
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldList[oldItemPosition].email != newList[newItemPosition].email -> {
                false
            }oldList[oldItemPosition].name != newList[newItemPosition].name ->{
                false
            }oldList[oldItemPosition].lastName != newList[newItemPosition].lastName ->
                false
            else -> true
        }

    }
}