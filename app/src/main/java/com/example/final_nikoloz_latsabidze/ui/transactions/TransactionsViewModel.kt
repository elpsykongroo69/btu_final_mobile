package com.example.final_nikoloz_latsabidze.ui.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class TransactionsViewModel : ViewModel() {

    val firebaseAuth= Firebase.auth

    private val _text = MutableLiveData<String>().apply {
        value = "NOT IMPLEMENTED"
    }
    val text: LiveData<String> = _text
}