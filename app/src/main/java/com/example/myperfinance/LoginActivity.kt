package com.example.myperfinance

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.Parse
import com.parse.ParseUser


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // When user click on login button
        findViewById<Button>(R.id.btnLogIn).setOnClickListener{
            val username = findViewById<EditText>(R.id.userName).text.toString()
            val password = findViewById<EditText>(R.id.userPass).text.toString()

            //call sign in function
            loginUser(username,password)
        }

        // When  user click on th sign up button
        findViewById<Button>(R.id.btnSignUp).setOnClickListener{
            val username = findViewById<EditText>(R.id.userPass).text.toString()
            val password = findViewById<EditText>(R.id.userPass).text.toString()
            signUpUser(username,password)
        }
    }

    private fun signUpUser(username: String, password: String) {
        // Create the ParseUser
        val user = ParseUser()

        // Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                // Hooray! Let them use the app now.
            } else {
                // Sign up didn't succeed. Look at the ParseException
                // to figure out what went wrong
                e.printStackTrace()
            }
        }
    }

    private fun loginUser(username: String, password: String) {

        ParseUser.logInInBackground(username,password,({user, e->
            if (user != null) {
                // Hooray!  The user is logged in.
                Log.i(TAG,"Sucessfully logged in!!")
                gotoMainActivity()
            } else {
                // Signup failed.  Look at the ParseException to see what happened.
                e.printStackTrace()
                Toast.makeText(this,"Error logging in", Toast.LENGTH_SHORT).show()
            }}))

    }

    private fun gotoMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}