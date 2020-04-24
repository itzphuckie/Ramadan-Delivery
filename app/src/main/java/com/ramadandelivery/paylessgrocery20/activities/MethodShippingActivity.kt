package com.ramadandelivery.paylessgrocery20.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ramadandelivery.paylessgrocery20.R
import kotlinx.android.synthetic.main.activity_method_shipping.*
import kotlinx.android.synthetic.main.app_bar.*

class MethodShippingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_method_shipping)
        init()
    }
    private fun init(){
        setupToolbar()
        check_box_delivery.setOnClickListener {
            check_box_delivery.isChecked = false
            startActivity(Intent(this,CheckoutShippingActivity::class.java))
        }
    }
    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = "Shipping Method "
        setSupportActionBar(toolbar)
        // give you an instance of the too lbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
//            R.id.menuViewCart -> {
//                var intent2cart = Intent(this, CartActivity::class.java)
//                startActivity(intent2cart)
//            }
            R.id.menu_home_1 ->{
                var intent = Intent(this,CategoryActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
