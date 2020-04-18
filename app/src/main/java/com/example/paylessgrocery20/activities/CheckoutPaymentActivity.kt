package com.example.paylessgrocery20.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.paylessgrocery20.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.activity_checkout_confirmation.*
import kotlinx.android.synthetic.main.app_bar.*

class CheckoutPaymentActivity : AppCompatActivity() {
    var cardType = ""
    var cardNum =""
    var cardVIN = ""
    var cardEXP = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        setupToolbar()
        confirm()
        //setBottomNavigation()
        //navViewBttom.setOnNavigationItemSelectedListener(myOnNavigationItemSelectedListener)
    }
    private fun checkEditTextValidation():Boolean{
        var notEmpty = true
        if(edit_text_card_type.text.toString().isEmpty())
        {
            notEmpty = false
            Toast.makeText(this,"Zip Code cannot be blank",Toast.LENGTH_SHORT).show()
        }
        if(edit_Text_card_numer.text.toString().isEmpty())
        {
            notEmpty = false
            Toast.makeText(this,"Card Number cannot be blank",Toast.LENGTH_SHORT).show()
        }
        if(edit_Text_VIN_number.text.toString().isEmpty())
        {
            notEmpty = false
            Toast.makeText(this,"VIN cannot be blank",Toast.LENGTH_SHORT).show()
        }

        if(edit_Text_EXP_Date.text.toString().isEmpty())
        {
            notEmpty = false
            Toast.makeText(this,"EXP Date cannot be blank.",Toast.LENGTH_SHORT).show()
        }

        else{
            //Toast.makeText(this,"All Filled",Toast.LENGTH_SHORT).show()
            notEmpty = true
        }
        return notEmpty
    }
    private fun confirm(){
        button_next_confirm_payment.setOnClickListener {
            if(checkEditTextValidation()){
                cardType = edit_text_card_type.text.toString()
                cardNum = edit_Text_card_numer.text.toString()
                cardVIN = edit_Text_VIN_number.text.toString()
                cardEXP = edit_Text_EXP_Date.text.toString()
                //var shippingAddress = ShippingAddress("Phuc",address,city,state,zipcode)
                var sharePref = getSharedPreferences("payment", Context.MODE_PRIVATE)
                var editor = sharePref.edit()
                editor.putString("cardType",cardType)
                editor.putString("cardNum",cardNum)
                editor.putString("cardVIN",cardVIN)
                editor.putString("cardEXP",cardEXP)
                editor.apply()
                //button_next_confirm_payment.setBackgroundColor(resources.getColor(R.color.colorButtonOnClick))
                startActivity(Intent(this,CheckoutConfirmationActivity::class.java))
            }

        }
    }
    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = "Payment Method"
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
