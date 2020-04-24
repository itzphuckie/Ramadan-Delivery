package com.ramadandelivery.paylessgrocery20.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.ramadandelivery.paylessgrocery20.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_request_password.*

class RequestPasswordActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_password)
        auth = FirebaseAuth.getInstance()
        init()
    }
    private fun init() {
        button_reset.setOnClickListener {
            var email = edit_text_email_reset.text.toString()
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(object: OnCompleteListener<Void> {
                    override fun onComplete(task: Task<Void>) {
                        if(task.isSuccessful){
                            Toast.makeText(applicationContext,"Email sent", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@RequestPasswordActivity,LoginActivity::class.java))
                        }
                        else{
                            Toast.makeText(applicationContext,"Oops. Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menuViewCart -> {
                var intent2cart = Intent(this, CartActivity::class.java)
                startActivity(intent2cart)
            }
            R.id.menuHome ->{
                var intent = Intent(this,CategoryActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
