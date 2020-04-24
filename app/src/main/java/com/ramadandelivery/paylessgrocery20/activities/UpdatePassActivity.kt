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
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_update_pass.*
import kotlinx.android.synthetic.main.app_bar.*

class UpdatePassActivity : AppCompatActivity() {
    //lateinit var auth: FirebaseAuth
    lateinit var user:FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_pass)
        //auth = FirebaseAuth.getInstance().currentUser
        user = FirebaseAuth.getInstance().currentUser as FirebaseUser
        var newPass = edit_text_confirm_new_password.text.toString()
        button_update_pass_info.setOnClickListener {
           // auth.currentUser
            //var email = auth.currentUser!!.email
            user.updatePassword(edit_text_confirm_new_password.text.toString()).addOnCompleteListener(object: OnCompleteListener<Void> {
                override fun onComplete(task: Task<Void>) {
                    if(task.isSuccessful){
                        Toast.makeText(applicationContext,"Password Updated", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(applicationContext,"Oops. Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        //init()
        setupToolbar()
        //setBottomNavigation()
        //navViewBttom.setOnNavigationItemSelectedListener(myOnNavigationItemSelectedListener)
    }

    private fun init() {

    }


//    private val myOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{ item ->
//        when (item.itemId) {
//            R.id.navigation_home ->{
//                startActivity(Intent(this,CategoryActivity::class.java))
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_profile -> {
//                startActivity(Intent(this,MyAccountActivity::class.java))
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_cart -> {
//                startActivity(Intent(this,CartActivity::class.java))
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_reward -> {
//                startActivity(Intent(this,RewardActivity::class.java))
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_signout -> {
//                startActivity(Intent(this,MainActivity::class.java))
//                return@OnNavigationItemSelectedListener true
//            }
//        }
//        false
//    }
    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = "Updating Password"
        setSupportActionBar(toolbar)
        // give you an instance of the too lbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
