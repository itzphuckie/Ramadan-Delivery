package com.ramadandelivery.paylessgrocery20.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.ramadandelivery.paylessgrocery20.R
import kotlinx.android.synthetic.main.activity_splash_end.*

class SplashEndActivity : AppCompatActivity() {
    var animation: Animation?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_end)

        animation = AnimationUtils.loadAnimation(applicationContext,R.anim.move2)
        image_view_slash_end.animation
        image_view_slash_end.startAnimation(animation)

        var handler = Handler()
        handler.postDelayed({
            changeToThankyou()
        },5000)
    }
    private fun changeToThankyou(){
        var intent = Intent(this,ThankYouActivity::class.java)
        progress_bar_splash_end_activity.visibility = View.GONE
        startActivity(intent)
        finish()
    }
}
