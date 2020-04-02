package com.example.emailpasswordauthapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import kotlinx.android.synthetic.main.activity_user.*


class HomeActivity : AppCompatActivity() {
    var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        firebaseAuth = FirebaseAuth.getInstance()

        btn_logOut.setOnClickListener {
            //On click, sign out by using Firebase and return to Login activity
            firebaseAuth?.signOut()
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
    }
}