package com.ramadandelivery.paylessgrocery20.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ramadandelivery.paylessgrocery20.R
import kotlinx.android.synthetic.main.activity_method_payment.*
import kotlinx.android.synthetic.main.app_bar.*

class MethodPaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_method_payment)
        init()
    }

    private fun init() {
        setupToolbar()
        check_box_cash.setOnClickListener {
            check_box_cash.isChecked = false
            var intent = Intent(this,CheckoutConfirmationActivity::class.java)
            //intent.putExtra("Payment",Payment("cash","Completed"))
            startActivity(intent)
        }
        check_box_card.setOnClickListener {
            check_box_card.isChecked = false
            startActivity(Intent(this,CheckoutPaymentActivity::class.java))
        }
    }
    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = "Payment Method "
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
