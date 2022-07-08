package com.example.tbc_course_11

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import com.example.tbc_course_11.adapter.CustomAdapter
import com.example.tbc_course_11.databinding.ActivityMainBinding
import com.example.tbc_course_11.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance().getReference("/User")
    private lateinit var userArrayList: ArrayList<User>
    private lateinit var adapterMain:RecyclerView



    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)





        showUsers()

        binding.floatingAddBtn.setOnClickListener {
            addUser()
        }


    }

    private fun addUser() {
        dialog()
    }

    private fun showUsers(){
        adapterMain = binding.mainRecycler
        adapterMain.setHasFixedSize(true)


        userArrayList = arrayListOf<User>()
        getUserData()

    }

    private fun getUserData() {
        database.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    for (userSnapshot in p0.children){
                        val user = userSnapshot.getValue(User::class.java)
                        userArrayList.add(user!!)
                    }

                    adapterMain.adapter = CustomAdapter(userArrayList)
                }

            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    //TODO set custom dialog
    //TODO new Class for dialog/db
    //TODO recreate animation
    //TODO function for dialog add
    //TODO ADD REMOVE BUTTON TO CARD_LAYOUT


    private fun dialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.add_user))

        val name = AppCompatEditText(this)
        val lastName = AppCompatEditText(this)
        val email = AppCompatEditText(this)
        val layout = LinearLayout(this)
        
        layout.apply {
            orientation = LinearLayout.VERTICAL

        }
        name.apply {
            InputType.TYPE_CLASS_TEXT
            hint = context.getString(R.string.your_name)

        }
        lastName.apply {
            inputType = InputType.TYPE_CLASS_TEXT
            hint = context.getString(R.string.your_last_name)
        }
        email.apply {
            inputType = InputType.TYPE_CLASS_TEXT
            hint = context.getString(R.string.your_email)
        }

        layout.addView(name)
        layout.addView(lastName)
        layout.addView(email)
        builder.setView(layout)


        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, which ->
                val userInfo = User(name.text.toString(),lastName.text.toString(),email.text.toString())
                database.child("${name.text.toString()} + ${lastName.text.toString()}").setValue(userInfo)
                this.recreate()


            })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }


}