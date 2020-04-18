package com.example.paylessgrocery20.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.paylessgrocery20.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.app_bar.*

class UpdateAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_account)
        setupToolbar()

    }


    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = "Updating Account"
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
