package com.csci5708.maylborrow.ui.startpage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.csci5708.maylborrow.MainActivity
import com.csci5708.maylborrow.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SplashscreenActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        val iv_note = findViewById<ImageView>(R.id.iv_note) as ImageView
        iv_note.alpha = 0f
        iv_note.animate().setDuration(1800).alpha(1f).withEndAction {

            auth = FirebaseAuth.getInstance()
            db = FirebaseFirestore.getInstance()
            if (auth.getCurrentUser() != null) {
                val uid = auth.currentUser?.uid.toString()
                val sharedPref = getSharedPreferences("user", Context.MODE_PRIVATE)
                val intent = Intent(this, MainActivity::class.java)

                val email = sharedPref.getString("email","1")
                if(email=="1"){
                    with(sharedPref.edit()){
                        db.collection("User").document(uid).get().addOnSuccessListener {
                                tasks ->
                            putString("email",tasks.get("email").toString())

                            apply()
                        }
                    }

                }

                startActivity(intent)
                finish()
            } else {
                val i = Intent(this, FirstPageActivity ::class.java)
                startActivity(i)
                finish()

            }
        }

    }

        }
