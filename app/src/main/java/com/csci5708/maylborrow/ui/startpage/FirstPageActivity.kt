package com.csci5708.maylborrow.ui.startpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.csci5708.maylborrow.R
import com.csci5708.maylborrow.databinding.ActivityFirstPageBinding
import com.csci5708.maylborrow.ui.login.LoginActivity
import com.csci5708.maylborrow.ui.signup.Signup
import com.google.firebase.auth.FirebaseAuth

class FirstPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstPageBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Signupbtn.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        binding.Loginbtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}