package com.example.paylessgrocery20.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.paylessgrocery20.R
import com.example.paylessgrocery20.app.Config
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    // Creating the object of firebase
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        init()
//        signUp_button.setOnClickListener {
//            var intentSignUp = Intent(this@RegisterActivity, LoginActivity::class.java)
//            var firstName = register_name.text.toString()
//            var email = register_email.text.toString()
//            var password = register_password.text.toString()
//            var mobile = register_mobile.text.toString()
//
//            if(email.isEmailValid()){
//                //Toast.makeText(this,"Valid Email!!! ",Toast.LENGTH_LONG).show()
//                if(isPasswordValid(password).equals(true)){
//                    register(firstName, email, password, mobile)
//                    startActivity(intentSignUp)
//                }
//                else{
//                    Toast.makeText(this,"Password needs to be 6 characters long !!! ",Toast.LENGTH_LONG).show()
//                }
//            }
//            else{
//                Toast.makeText(this,"Invalid Email !!! ",Toast.LENGTH_LONG).show()
//            }
//        }

    }

    private fun init() {
        signUp_button.setOnClickListener {
            var firstName = register_name.text.toString()
            var mobile = register_mobile.text.toString()
            var email = register_email.text.toString()
            var password = register_password.text.toString()
            if(firstName.isEmpty() || mobile.isEmpty() || email.isEmpty() || password.isEmpty() ){
                Toast.makeText(applicationContext,"Please fill out all requirements",Toast.LENGTH_SHORT).show()
            }
            else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {
                        override fun onComplete(task: Task<AuthResult>) {
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    applicationContext,
                                    "Register Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(
                                    Intent(
                                        this@RegisterActivity,
                                        LoginActivity::class.java
                                    )
                                )
                            } else {
                                if (password.length < 6) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Your password needs to be at least 6 characters",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        applicationContext,
                                        "Your email is not valid",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }
                        }
                    })
            }
        }
        already_register_text_view.setOnClickListener {
            var intentSignUp = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intentSignUp)
        }
    }

    private fun register(firstName: String, email: String, password: String, mobile: String) {
        var params = HashMap<String, String>()
        params.put("email", email)
        params.put("password", password)
        params.put("firstName", firstName)
        params.put("mobile", mobile)
        var data = JSONObject(params as Map<*, *>)
        var requestQueue = Volley.newRequestQueue(this)

        var request = JsonObjectRequest(
            Request.Method.POST, Config.REGISTER_URL, data,
            Response.Listener {
                Toast.makeText(this,"User Registered", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {
                Toast.makeText(this,"User cannot register", Toast.LENGTH_SHORT).show()
            })
        requestQueue.add(request)

    }
    // Checking for valid email
    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
    // Checking for valid password
    fun isPasswordValid(password: String): Boolean {
        var correctFormat = true
        if(password.length < 6){
            correctFormat = false
        }
        else{
            correctFormat = true
        }
        return correctFormat
    }
}
