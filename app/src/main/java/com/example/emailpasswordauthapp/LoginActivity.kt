package com.example.emailpasswordauthapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    var firebaseAuth: FirebaseAuth? = null
    private var authStateListener: AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth = FirebaseAuth.getInstance()

//        For AuthStateListener, its listener will be called when there is a change in the
//        authentication state, will be call when:
//          - Right after the listener has been registered
//          - When a user is signed in
//          - When the current user is signed out
//          - When the current user changes
        authStateListener = AuthStateListener { firebaseAuth ->
            //Check if there is a user has already logged in, if so, redirect to Home activity
            val user = firebaseAuth.currentUser
            if (user != null) {
                Toast.makeText(this, "User logged in, access granted", Toast.LENGTH_SHORT).show()
                val i = Intent(this, HomeActivity::class.java)
                startActivity(i)
            } else {
                Toast.makeText(this, "Login to continue", Toast.LENGTH_SHORT).show()
            }
        }

        tv_signUp.setOnClickListener {
            //On click, redirect to Sign Up activity
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
        }

        btn_logIn.setOnClickListener {
            //On click, verify mandatory data, then try log in, and if that is ok, redirect
            //to Home activity
            val userEmail: String = et_loginEmail.text.toString()
            val userPass: String = et_loginPass.text.toString()

            if (userEmail.isEmpty()) {
                et_loginEmail.error = "Complete Email input"
                et_loginEmail.requestFocus()

            } else if (userPass.isEmpty()) {
                et_loginPass.error = "Complete Password input"
                et_loginPass.requestFocus()

            } else if (userEmail.isEmpty() && userPass.isEmpty()) {
                Toast.makeText(this, "Complete Email and Password input", Toast.LENGTH_SHORT).show()

            } else if (!(userEmail.isEmpty() && userPass.isEmpty())) {
                //Log user by using Firebase registered user list
                firebaseAuth!!.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(this) { task ->
                        if (!task.isSuccessful) {
                            Toast.makeText(this, "Not sucessful", Toast.LENGTH_SHORT).show()
                        } else {
                            startActivity( Intent( this, HomeActivity::class.java))
                        }
                    }
            } else {
                Toast.makeText(this, "Unknown error...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        //Register listener in for Firebase Authentication
        firebaseAuth?.addAuthStateListener(authStateListener!!)
    }
}