package com.example.tbc_course_11

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import com.example.tbc_course_11.databinding.ActivityUserBinding
import com.example.tbc_course_11.models.User
import com.google.firebase.database.FirebaseDatabase

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private val database = FirebaseDatabase.getInstance().getReference("/User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val getName = intent.getStringExtra("name").toString()
        val getLastName = intent.getStringExtra("lastName").toString()
        val getEmail = intent.getStringExtra("email").toString()



        if (getName != "null") {
            binding.nameEditText.apply {
                setText(getName)
                isFocusable = false
            }
            binding.lastNameEditText.apply {
                setText(getLastName)
                isFocusable = false
            }
            binding.emailEditText.setText(getEmail)
            binding.userActivityTextView.text = getString(R.string.update_user)


        }

        binding.saveBtn.setOnClickListener {

            validator(
                binding.nameEditText.text.toString(),
                binding.lastNameEditText.text.toString(),
                binding.emailEditText.text.toString()
            )
        }


    }

    private fun intent() {
        val intent = Intent(this, UsersActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun validator(name: String, lastName: String, email: String) {
        if (!isAllFilled() && isEmailValid()) {
            addToDb(name, lastName, email)
            binding.emailInputLayout.error = null
            intent()
        } else {
            binding.emailInputLayout.error = getString(R.string.filled)
        }
    }

    private fun isAllFilled(): Boolean = with(binding) {
        return@with binding.emailEditText.text.toString().isEmpty() ||
                binding.nameEditText.text.toString().isEmpty() ||
                binding.lastNameEditText.text.toString().isEmpty()

    }

    private fun isEmailValid(): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailEditText.text.toString())
            .matches()

    private fun addToDb(name: String, lastName: String, email: String) {
        val userInfo = User(name, lastName, email)
        database.child("$name $lastName").setValue(userInfo)

    }
}