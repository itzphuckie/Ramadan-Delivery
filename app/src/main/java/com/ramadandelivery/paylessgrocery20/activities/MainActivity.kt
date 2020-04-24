package com.ramadandelivery.paylessgrocery20.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ramadandelivery.paylessgrocery20.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var isCheckedRemember = intent.getBooleanExtra("isChecked",false)
        // If already is already Logged in, take to home activity
        auth = FirebaseAuth.getInstance()
//        if(isCheckedRemember){
//            if(auth.currentUser != null){
//            startActivity(Intent(this@MainActivity,CategoryActivity::class.java))
//            }
//        }
        changeView()
    }

    private fun changeView(){
        login_button.setOnClickListener {
            //login_button.setBackgroundColor(resources.getColor(R.color.colorButtonOnClick))
            //login_button.setBackgroundColor(Color.GRAY)
            var intentLogin = Intent(this@MainActivity, LoginActivity::class.java)
            //intentSecond.putExtra("DATA", )
            startActivity(intentLogin)
        }
        register_button.setOnClickListener {
            //register_button.setBackgroundColor(resources.getColor(R.color.colorButtonOnClick))
            //login_button.setBackgroundColor(Color.GRAY)
            var intentRegister = Intent(this@MainActivity, RegisterActivity::class.java)
            //intentSecond.putExtra("DATA", )
            startActivity(intentRegister)
        }
    }
}
