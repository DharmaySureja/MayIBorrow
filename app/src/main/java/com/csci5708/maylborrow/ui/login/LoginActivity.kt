package com.csci5708.maylborrow.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.R
import com.csci5708.maylborrow.MainActivity
import com.csci5708.maylborrow.databinding.ActivityForgotPassBinding
import com.csci5708.maylborrow.databinding.ActivityLoginBinding
import com.csci5708.maylborrow.ui.home.HomeFragment
import com.csci5708.maylborrow.ui.signup.Signup
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var fpbinding: ActivityForgotPassBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.textView5.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }
        binding.forgotpass.setOnClickListener{

            fpbinding = ActivityForgotPassBinding.inflate(layoutInflater)
            setContentView(fpbinding.root)
            val reemail: EditText = fpbinding.resetemail
            val rebutton: Button = fpbinding.button2
            rebutton.setOnClickListener{
                firebaseAuth.sendPasswordResetEmail(reemail.getText().toString()).addOnSuccessListener {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Please Check your email", Toast.LENGTH_SHORT).show()
                }
                    .addOnFailureListener {
                        Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                    }
            }

        }

        binding.button.setOnClickListener {
            val email = binding.loginemail.text.toString()
            val pass = binding.loginpass.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Please enter correct credentials", Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onStart() {
        super.onStart()

//        if(firebaseAuth.currentUser != null){
//            val intent = Intent(this, HomeFragment::class.java)
//            startActivity(intent)
//        }
    }
}
