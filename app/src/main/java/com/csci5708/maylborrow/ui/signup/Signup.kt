package com.csci5708.maylborrow.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.csci5708.maylborrow.R
import com.csci5708.maylborrow.databinding.ActivitySignupBinding
import com.csci5708.maylborrow.model.UserModel
import com.csci5708.maylborrow.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class Signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var firstname: String
    private lateinit var lastname: String
    private lateinit var address: String
    private lateinit var phonenumber: String
    private lateinit var email: String
    private lateinit var password: String
    private  lateinit var confirmpassword: String





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)

        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        binding.Register.setOnClickListener {
            email = binding.email.text.toString()
            password = binding.password.text.toString()
            confirmpassword =binding.confirmpassword.text.toString()
            firstname = binding.FirstName.text.toString()
            lastname = binding.LastName.text.toString()
            phonenumber = binding.MobileNumber.text.toString()
            address = binding.Address.text.toString()
            validateData()
        }


        }

    private fun FirebaseSignup(){
        if (email.isNotEmpty() && password.isNotEmpty() && confirmpassword.isNotEmpty()) {
            if (password == confirmpassword) {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        saveUserData()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun validateData() {

        if(firstname.count()<2){
            if (firstname.isEmpty()){
                binding.FirstName.error = "Please enter firstname"
            }else{
                binding.FirstName.error = "Firstname must be more than 2 letters"
            }

        }else if(phonenumber.count()<10){
            if (phonenumber.isEmpty()){
                binding.MobileNumber.error="Phone number cannot be empty"
            }else{
                binding.MobileNumber.error="Phone number must be 10 digits"
            }

        }else if(lastname.isEmpty()){
            binding.LastName.error = "Lastname can't be empty"


        }else if(address.isEmpty()){
            binding.Address.error = "Address can't be empty"
        }else{
            FirebaseSignup()
        }


    }
    private fun saveUserData(){
        val user = UserModel(email,firstname,lastname, phonenumber, address)
        db.collection("User").document(email).set(user)

    }


}

