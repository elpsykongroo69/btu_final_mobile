package com.example.final_nikoloz_latsabidze.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class HomeViewModel : ViewModel() {

    val firebaseAuth= Firebase.auth
    var database: FirebaseDatabase = FirebaseDatabase.getInstance("https://testlect-4955f-default-rtdb.europe-west1.firebasedatabase.app/")


    private val _text = MutableLiveData<String>().apply {

        //fetch the value of balance of current user
        val email=firebaseAuth.currentUser?.email.toString()
        val balance = database.getReference("users").child(email.substring(0, email.indexOf("@"))).get().addOnSuccessListener {
            val balance = it.child("balance").value.toString()
            value = "Your balance is: $balance"
        }

    }
    val text: LiveData<String> = _text
}