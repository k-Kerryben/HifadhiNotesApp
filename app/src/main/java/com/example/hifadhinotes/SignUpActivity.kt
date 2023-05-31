package com.example.hifadhinotes

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    lateinit var progress:ProgressDialog
    lateinit var mbtnsignin:Button
    lateinit var msignup:Button
    lateinit var mname:EditText
    lateinit var muser:EditText
    lateinit var memail:EditText
    lateinit var mpass:EditText
    lateinit var mrpass:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mbtnsignin = findViewById(R.id.signinbtn)
        mname = findViewById(R.id.name)
        muser = findViewById(R.id.username)
        memail = findViewById(R.id.email)
        mpass = findViewById(R.id.password)
        mrpass = findViewById(R.id.rpassword)
        msignup = findViewById(R.id.signupbtn)
        mAuth = FirebaseAuth.getInstance()
        progress.setTitle("Loading..")
        progress.setMessage("Please..wait..")

        msignup.setOnClickListener {
        var name:String = mname.text.toString().trim()
        var user:String = muser.text.toString().trim()
        var email:String = memail.text.toString().trim()
        var password:String = mpass.text.toString().trim()
        var rpassword:String = mrpass.text.toString().trim()

        if (name.isEmpty()) {
            mname.error = "Please fill the space"
            mname.requestFocus()
        } else if (email.isEmpty()) {
            memail.error = "Please fill the space"
            memail.requestFocus()
        } else if (user.isEmpty()) {
            muser.error = "Please fill the space"
           muser.requestFocus()
        }else if (password.isEmpty()) {
            mpass.error = "Please fill the space"
            mpass.requestFocus()
        } else if (rpassword.isEmpty()) {
            mrpass.error = "Input required"
            mrpass.requestFocus()
        } else {


            progress.show()
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    progress.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this, "Successful Registration",
                            Toast.LENGTH_SHORT).show()
                    mAuth.signOut()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }else{
                        displayusers("ERROR", it.exception!!.message.toString())
                    }
                }
        }

        }
        mbtnsignin.setOnClickListener {
            var click = Intent(this, MainActivity::class.java)
            startActivity(click)
        }
    }

    fun displayusers(title:String, message:String ) {
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("OK", null)
        alertDialog.create().show()
    }
}