package com.example.final_nikoloz_latsabidze

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    lateinit var signUpEmailEditText: EditText
    lateinit var signUpPasswordEditText: EditText
    lateinit var signUpButton: Button
    lateinit var goToLoginButton: Button
    lateinit var nameEditText: EditText

    val firebaseAuth= Firebase.auth
    lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        database= FirebaseDatabase.getInstance("https://testlect-4955f-default-rtdb.europe-west1.firebasedatabase.app/")
        init()
        listeners()


    }


    private fun init(){
         signUpEmailEditText= findViewById(R.id.signUpEmailEditText)
         signUpPasswordEditText= findViewById(R.id.signUpPasswordEditText)
         signUpButton= findViewById(R.id.signUpButton)
         goToLoginButton= findViewById(R.id.goToLoginButton)
         nameEditText= findViewById(R.id.nameEditText)

    }

    private fun listeners(){
        signUpButton.setOnClickListener {
            val email = signUpEmailEditText.text.toString()
            val password = signUpPasswordEditText.text.toString()
            val name = nameEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Email or password must not be empty", Toast.LENGTH_SHORT).show()
            }
            if(name.isEmpty()){
                Toast.makeText(this, "Name must not be empty", Toast.LENGTH_SHORT).show()
            }

            // register user using firebase
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Successfully registered!", Toast.LENGTH_SHORT).show()
                        //Save user in database
                        val user = User(name, email,100.0,firebaseAuth.currentUser?.uid.toString())
                        //remove everything after @
                        database.getReference("users").child(email.substring(0, email.indexOf("@"))).setValue(user)

                        startActivity(Intent(this, LoginActivity::class.java))
                    } else {
                        Toast.makeText(this, "Error During Registering", Toast.LENGTH_SHORT).show()
                    }
                }









        }

        goToLoginButton.setOnClickListener {

            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}