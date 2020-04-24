package com.ramadandelivery.paylessgrocery20.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ramadandelivery.paylessgrocery20.R
import com.ramadandelivery.paylessgrocery20.service.MediaPlayerService
import java.io.IOException
import java.util.*


class RecorderActivity : AppCompatActivity() {

    lateinit var submit : Button
    lateinit var outputFile: String


    lateinit var record_stop: Button
    lateinit var play_stop: Button
    lateinit var myAudioRecorder: MediaRecorder
    lateinit var myMediaPlayer: MediaPlayer


    val REQUEST_PERMISSION_CODE = 1000



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recorder)

        stopService(Intent(this, MediaPlayerService::class.java))

        var ifrecording = false
        var ifplaying = false

        record_stop = findViewById(R.id.record_and_stop)
        play_stop = findViewById(R.id.play_and_stop)
        submit = findViewById(R.id.save_record)

        play_stop.isEnabled=false

        if (ifrecording == false) {
            record_stop.text = "record"
            play_stop.text="play"
            play_stop.isEnabled = false

        }


        record_stop.setOnClickListener {
            if (ifrecording == false) {
                record_stop.text = "recording...."
                play_stop.isEnabled=false

                if (checkPermissionFromDevice()) {

                    outputFile =
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "_audio_record.3gp"


                    Log.d("outputFile", outputFile)


                    setUpMediaRecorder()

                    try {
                        myAudioRecorder.prepare()
                        myAudioRecorder.start()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }


                    Toast.makeText(this, "recording", Toast.LENGTH_SHORT).show()

                } else {
                    requestPermission()
                }

                submit.isEnabled=false

                ifrecording = true
            } else {
                myAudioRecorder.stop()
                myAudioRecorder.reset()
                myAudioRecorder.release()
                record_stop.text = "record"

                ifrecording = false
                play_stop.isEnabled = true
                submit.isEnabled =true
            }

        }

        play_stop.setOnClickListener {
            if (ifplaying == false) {
                submit.isEnabled=false
                play_stop.text = "playing"
                record_stop.isEnabled=false


                myMediaPlayer = MediaPlayer()

                try {
                    myMediaPlayer.setDataSource(outputFile)
                    myMediaPlayer.prepare()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                myMediaPlayer.start()
                Toast.makeText(this, "playing", Toast.LENGTH_SHORT).show()
                ifplaying = true

            } else {
                record_stop.isEnabled=true

                play_stop.text = "play"

                if (myMediaPlayer != null) {
                    myMediaPlayer.stop()
                    myMediaPlayer.release()
                    setUpMediaRecorder()

                }
                submit.isEnabled =true
                ifplaying = false

            }


        }

        submit.setOnClickListener {
            /*var intent = Intent(this,MainActivity::class.java)
            intent.putExtra("record_path",outputFile)
            startActivity(intent)*/
            finish()
        }


    }



    fun getNow(): String {
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            return SimpleDateFormat("yyyyMMddHHmmssSSS").format(Date())
        } else {
            var tms = Calendar.getInstance()
            return tms.get(Calendar.YEAR).toString() + tms.get(Calendar.MONTH).toString() + tms.get(
                Calendar.DAY_OF_MONTH
            ).toString() + tms.get(Calendar.HOUR_OF_DAY).toString() + tms.get(
                Calendar.MINUTE
            ).toString() + tms.get(Calendar.SECOND).toString() + tms.get(Calendar.MILLISECOND).toString()
        }

    }



    private fun setUpMediaRecorder() {
        myAudioRecorder = MediaRecorder()
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
        myAudioRecorder.setOutputFile(outputFile)

    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            REQUEST_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun checkPermissionFromDevice(): Boolean {
        var write_permission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        var record_permission =
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)

        var read_permission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )

        return write_permission == PackageManager.PERMISSION_GRANTED
                && record_permission == PackageManager.PERMISSION_GRANTED
                &&read_permission == PackageManager.PERMISSION_GRANTED

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        var view = window.decorView
        var lp =view.layoutParams as WindowManager.LayoutParams
        lp.gravity = Gravity.RIGHT
        windowManager.updateViewLayout(view,lp)

    }
}