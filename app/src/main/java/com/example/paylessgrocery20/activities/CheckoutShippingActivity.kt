package com.example.paylessgrocery20.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.paylessgrocery20.R
import kotlinx.android.synthetic.main.activity_checkout_shipping.*
import kotlinx.android.synthetic.main.activity_method_shipping.*
import kotlinx.android.synthetic.main.app_bar.*

class CheckoutShippingActivity : AppCompatActivity() {

    var address = ""
    var city = ""
    var state = ""
    var zipcode = ""
    var name = ""
    var notEmpty = true
    var phone = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_shipping)
        setupToolbar()
        confirm()
    }


    private fun checkEditTextValidation(): Boolean {
        if (edit_Text_name.text.toString().isEmpty()) {
            notEmpty = false
            Toast.makeText(this, "Shipping name cannot be blank", Toast.LENGTH_SHORT).show()
        }
        if (edit_Text_address.text.toString().isEmpty()) {
            notEmpty = false
            Toast.makeText(this, "Address cannot be blank", Toast.LENGTH_SHORT).show()
        }
        if (edit_Text_city.text.toString().isEmpty()) {
            notEmpty = false
            Toast.makeText(this, "City cannot be blank", Toast.LENGTH_SHORT).show()
        }
        if (edit_Text_zipcode.text.toString().isEmpty()) {
            notEmpty = false
            Toast.makeText(this, "Zip Code cannot be blank", Toast.LENGTH_SHORT).show()
        }
        if (edit_Text_phone_address.text.toString().isEmpty()) {
            notEmpty = false
            Toast.makeText(this, "Phone cannot be blank", Toast.LENGTH_SHORT).show()
        }else {
            //Toast.makeText(this,"All Filled",Toast.LENGTH_SHORT).show()
            notEmpty = true
        }
        return notEmpty
    }

    private fun confirm() {
        button_next_confirm_shipping.setOnClickListener {
            if (checkEditTextValidation()) {
                address = edit_Text_address.text.toString()
                city = edit_Text_city.text.toString()
                zipcode = edit_Text_zipcode.text.toString()
                name = edit_Text_name.text.toString()
                phone = edit_Text_phone_address.text.toString()
                //var shippingAddress = ShippingAddress("Phuc",address,city,state,zipcode)
                var sharePref = getSharedPreferences("shippingAddress", Context.MODE_PRIVATE)
                var editor = sharePref.edit()
                editor.putString("name", name)
                editor.putString("address", address)
                editor.putString("city", city)
                Log.d("Tester", "Testing for ediotr " + city)
                editor.putString("state", state)
                editor.putString("zipcode", zipcode)
                editor.putString("phone",phone)
                editor.apply()
                //button_next_confirm_shipping.setBackgroundColor(resources.getColor(R.color.colorButtonOnClick))
                startActivity(Intent(this, CheckoutConfirmationActivity::class.java))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_home_1 -> {
                var intent = Intent(this, CategoryActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        var toolbar = tool_bar
        toolbar.title = "Shipping Address "
        setSupportActionBar(toolbar)
        // give you an instance of the too lbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
