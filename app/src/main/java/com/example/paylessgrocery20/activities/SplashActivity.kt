package com.example.paylessgrocery20.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.paylessgrocery20.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    var animation: Animation?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        animation = AnimationUtils.loadAnimation(applicationContext,R.anim.move)
        image_view_slash.animation
        image_view_slash.startAnimation(animation)

        var handler = Handler()
        handler.postDelayed({
            checkLogin()
        },3000)
    }

    private fun checkLogin(){
        var intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
