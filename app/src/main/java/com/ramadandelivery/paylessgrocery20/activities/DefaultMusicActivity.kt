package com.ramadandelivery.paylessgrocery20.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.ramadandelivery.paylessgrocery20.R
import com.ramadandelivery.paylessgrocery20.service.MediaPlayerService
import kotlinx.android.synthetic.main.activity_default_music.*

class DefaultMusicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default_music)

        play_music.setOnClickListener {
            startService(Intent(this, MediaPlayerService::class.java))
        }

        stop_play_music.setOnClickListener {
            stopService(Intent(this,MediaPlayerService::class.java))
        }

        go_back_home.setOnClickListener {
            startActivity(Intent(this,RCActivity::class.java))
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        var view = window.decorView
        var lp =view.layoutParams as WindowManager.LayoutParams
        lp.gravity = Gravity.LEFT
        windowManager.updateViewLayout(view,lp)




    }
}

