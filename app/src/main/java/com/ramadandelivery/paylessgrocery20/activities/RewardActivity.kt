package com.ramadandelivery.paylessgrocery20.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.Toast
import com.ramadandelivery.paylessgrocery20.R
import kotlinx.android.synthetic.main.app_bar.*

class RewardActivity : AppCompatActivity() {
    //lateinit var navViewBttom: BottomNavigationView
    var totalAmount = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward)
        setupToolbar()
        var sharePrefOrderSummary = getSharedPreferences("orderSummary", Context.MODE_PRIVATE)
        totalAmount = sharePrefOrderSummary.getString("totalAmount","").toString()

        val seek = findViewById<SeekBar>(R.id.seekBar)
        seek?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {

                // write custom code for progress is changed
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                seek.progress = 75
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                Toast.makeText(this@RewardActivity, "Reward is: " + seek.progress + "%", Toast.LENGTH_SHORT).show()
            }
        })
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
    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = "Rewards"
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
