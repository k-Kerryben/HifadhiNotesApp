package com.example.hifadhinotes

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.app.ProgressDialog
import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var mAuth:FirebaseAuth
    lateinit var progress: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val musername = findViewById<EditText>(R.id.username)
        val mpassword = findViewById<EditText>(R.id.password)
        val msignup = findViewById<Button>(R.id.btnsignup)
        val mlogin = findViewById<MaterialButton>(R.id.loginbtn)
        mAuth = FirebaseAuth.getInstance()

    mlogin.setOnClickListener {
        var email:String = musername.text.toString().trim()
        var password:String = mpassword.text.toString().trim()

        if (email.isEmpty()) {
            musername.error = "Please fill the space"
            musername.requestFocus()
        } else if (password.isEmpty()) {
            mpassword.error = "Input required"
            mpassword.requestFocus()
        } else {
            progress.show()
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    progress.dismiss()
                    if (it.isSuccessful) {
                        Toast.makeText(
                            this, "Successful Registration",
                            Toast.LENGTH_SHORT
                        ).show()
                        mAuth.signOut()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        displaymessage("ERROR", it.exception!!.message.toString())
                    }
                }
        }
    }

        msignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }


    }


    fun displaymessage(title:String, message:String ) {
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("OK", null)
        alertDialog.create().show()
    }

}



