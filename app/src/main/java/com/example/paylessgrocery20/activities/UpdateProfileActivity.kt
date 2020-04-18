package com.example.paylessgrocery20.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.example.paylessgrocery20.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_update_profile.*
import kotlinx.android.synthetic.main.app_bar.*

class UpdateProfileActivity : AppCompatActivity() {
    //lateinit var navViewBttom: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)
        changeUpdateView()
        setupToolbar()
        //setBottomNavigation()
        //navViewBttom.setOnNavigationItemSelectedListener(myOnNavigationItemSelectedListener)
    }
//    private fun setBottomNavigation(){
//        navViewBttom = findViewById(R.id.nav_bottom_view)
//    }
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
    private fun changeUpdateView(){
        button_update_account.setOnClickListener {
            startActivity(Intent(this,UpdateAccountActivity::class.java))
        }
        button_update_pass.setOnClickListener {
            startActivity(Intent(this,UpdatePassActivity::class.java))
        }
        button_update_address.setOnClickListener {
            startActivity(Intent(this,UpdateAddressActivity::class.java))
        }
        button_update_payment.setOnClickListener {
            startActivity(Intent(this,UpdatePaymentActivity::class.java))
        }
    }
    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = "Update Profile"
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
