package com.ramadandelivery.paylessgrocery20.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.ramadandelivery.paylessgrocery20.R

class MediaPlayerService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    lateinit var mediaPlayer : MediaPlayer


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer.start()
        return super.onStartCommand(intent, flags, startId)

    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer= MediaPlayer.create(this, R.raw.azan1)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()

    }
}