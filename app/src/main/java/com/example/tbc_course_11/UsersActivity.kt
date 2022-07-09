package com.example.tbc_course_11

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tbc_course_11.adapter.CustomAdapter
import com.example.tbc_course_11.databinding.ActivityUsersBinding
import com.example.tbc_course_11.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UsersActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance().getReference("/User")
    private lateinit var userArrayList: ArrayList<User>
    private lateinit var adapterMain: RecyclerView
    private lateinit var binding: ActivityUsersBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        showUsers()

        binding.floatingAddBtn.setOnClickListener {

            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }


    }

    private fun showUsers() {
        adapterMain = binding.mainRecycler
        adapterMain.setHasFixedSize(false)
        userArrayList = arrayListOf<User>()
        getUserData()

    }

    private fun getUserData() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {

                    for (userSnapshot in p0.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        userArrayList.add(user!!)

                    }

                    adapterMain.adapter = CustomAdapter(userArrayList)
                }

            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }


}