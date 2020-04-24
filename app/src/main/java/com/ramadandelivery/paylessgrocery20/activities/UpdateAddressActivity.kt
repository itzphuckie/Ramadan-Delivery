package com.ramadandelivery.paylessgrocery20.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ramadandelivery.paylessgrocery20.R
import com.ramadandelivery.paylessgrocery20.models.ShippingAddress
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update_address.*
import kotlinx.android.synthetic.main.app_bar.*

class UpdateAddressActivity : AppCompatActivity() {
    var userID = ""
    var newName = ""
    var newAddress = ""
    var newCity = ""
    var newState = ""
    var newZip = ""
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_address)
        setupToolbar()
        newName = edit_text_new_name.text.toString()
        newAddress = edit_text_new_address.text.toString()
        newCity = edit_text_new_city.text.toString()
        newState = edit_text_new_state.text.toString()
        newZip = edit_text_new_zipcode.text.toString()
        var sharePrefUserId = getSharedPreferences("emailID", Context.MODE_PRIVATE)
        userID = sharePrefUserId.getString("email","").toString()
        var ext = "."
        userID = userID.replace(ext,"")
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("orders").child(userID).child("shippingAddress")

        button_update_address_info.setOnClickListener {
            var newShippingAddress = ShippingAddress(newName,newAddress,newCity,newState,newZip,"N/A")
            databaseReference.setValue(newShippingAddress)
        }
    }



    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = "Updating Address"
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
