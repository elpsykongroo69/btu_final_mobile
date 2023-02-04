package com.example.final_nikoloz_latsabidze

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    lateinit var loginEmailEditText: EditText
    lateinit var loginPasswordEditText: EditText
    lateinit var loginButton: Button
    lateinit var loginGoToSignUp: Button



    val firebaseAuth= Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
        listeners()

    }


    private fun listeners() {
        loginButton.setOnClickListener {
            val email = loginEmailEditText.text.toString()
            val password = loginPasswordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                return@setOnClickListener
            }

            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    startActivity(Intent(this,MainActivity::class.java))
                }
                else{
                    Toast.makeText(this, "Wrong password or email!!!", Toast.LENGTH_SHORT).show()
                }
            }


        }
        loginGoToSignUp.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }
/*        loginForgotPasswordButton.setOnClickListener {
            startActivity(Intent(this,ForgotPasswordActivity::class.java))

        }*/
    }



    private fun init(){
        loginEmailEditText = findViewById(R.id.loginEmailEditText)
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText)
        loginButton = findViewById(R.id.loginButton)
        loginGoToSignUp = findViewById(R.id.loginGoToSignUp)

    }
}