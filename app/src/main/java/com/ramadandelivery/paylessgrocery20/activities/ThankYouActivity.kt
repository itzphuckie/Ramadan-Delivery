package com.ramadandelivery.paylessgrocery20.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.ramadandelivery.paylessgrocery20.R
import kotlinx.android.synthetic.main.activity_thank_you.*

class ThankYouActivity : AppCompatActivity() {
    var animation: Animation?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thank_you)
        animation = AnimationUtils.loadAnimation(applicationContext,R.anim.rotate)
        image_view_ty.animation
        image_view_ty.startAnimation(animation)

        //var orderID = intent.getStringExtra("orderID")

        var sharePrefUserOrderID = getSharedPreferences("orderID", Context.MODE_PRIVATE)
        var orderID = sharePrefUserOrderID.getString("orderID","")

        text_view_thankyou_orderID.text = "Your Order Number is " + orderID + " ."

        init()
    }

    override fun onBackPressed(){
    }
    override fun onRestart() {
        super.onRestart()
        finish()
    }
    private fun init(){
        button_thankyou_to_category.setOnClickListener {
            startActivity(Intent(this,CategoryActivity::class.java))
            finish()
        }
    }
}
