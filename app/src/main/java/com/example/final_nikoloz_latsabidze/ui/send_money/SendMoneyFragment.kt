package com.example.final_nikoloz_latsabidze.ui.send_money

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.final_nikoloz_latsabidze.R
import com.example.final_nikoloz_latsabidze.databinding.FragmentSendMoneyBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SendMoneyFragment : Fragment() {

    lateinit var sendMoneyAmountEditText: TextView
    lateinit var recipientEmailAddressEditText: EditText
    lateinit var sendMoneyButton: Button


    private var _binding: FragmentSendMoneyBinding? = null



    val firebaseAuth= Firebase.auth
    lateinit var database: FirebaseDatabase

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sendMoneyViewModel =
            ViewModelProvider(this).get(SendMoneyViewModel::class.java)

        _binding = FragmentSendMoneyBinding.inflate(inflater, container, false)
        val root: View = binding.root



        database= FirebaseDatabase.getInstance("https://testlect-4955f-default-rtdb.europe-west1.firebasedatabase.app/")
        initViews()
        listeners()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        sendMoneyAmountEditText = binding.sendMoneyAmountEditText
        recipientEmailAddressEditText = binding.recipientEmailAddressEditText
        sendMoneyButton = binding.sendMoneyButton
    }

    private fun listeners(){
        sendMoneyButton.setOnClickListener {
            val amount = sendMoneyAmountEditText.text.toString().toDouble()
            val recipient = recipientEmailAddressEditText.text.toString()
            val dbId=recipient.substring(0, recipient.indexOf("@"))
            //Check if I have enough money to send
            val myEmail=firebaseAuth.currentUser?.email.toString()
            database.getReference("users").child(myEmail.substring(0, myEmail.indexOf("@"))).get().addOnSuccessListener {
                val balance = it.child("balance").value.toString()
                if (balance.toDouble() < amount) {
                    Toast.makeText(context, "You don't have enough money", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
                else{
                    //find user in db using email and add amount to his balance
                    sendMoney(dbId, amount, myEmail)

                }
            }


        }
    }

    private fun sendMoney(dbId: String, amount: Double, myEmail: String) {
        database.getReference("users").child(dbId).child("balance").get().addOnSuccessListener {
            val newBalance = it.value.toString().toDouble() + amount
            database.getReference("users").child(dbId).child("balance").setValue(newBalance)
            //substract amount from my balance
            database.getReference("users").child(myEmail.substring(0, myEmail.indexOf("@")))
                .child("balance").get().addOnSuccessListener {
                    val myBalance = it.value.toString().toDouble() - amount
                    database.getReference("users").child(myEmail.substring(0, myEmail.indexOf("@")))
                        .child("balance").setValue(myBalance)
                }

            Toast.makeText(context, "Successfully Sent the money", Toast.LENGTH_LONG).show()

        }.addOnFailureListener {
            //if user not found toast message
            Toast.makeText(context, "Recipient with email not found", Toast.LENGTH_SHORT).show()
        }
    }
}