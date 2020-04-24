package com.ramadandelivery.paylessgrocery20.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ramadandelivery.paylessgrocery20.R
import com.ramadandelivery.paylessgrocery20.app.Config
import com.ramadandelivery.paylessgrocery20.models.User
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_my_account.*
import kotlinx.android.synthetic.main.app_bar.*

class MyAccountActivity : AppCompatActivity() {
    lateinit var profileDetails : User
    private val REQUEST_CAMERA = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)
        setupToolbar()
        init()
        changeToPassword()

        //getUser(Config.USER_ID)
    }

    private fun init() {
        image_view_camera_button.setOnClickListener {
            setUpPermission()
        }
    }

    private fun setUpPermission() {
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if(permission!= PackageManager.PERMISSION_GRANTED){
            //we don't have permission for camera hence request for camera permission
            requestCameraPermission()
        }else{
            openCamera()
        }
    }

    private fun openCamera() {
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,REQUEST_CAMERA)
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA
            ),
            REQUEST_CAMERA
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CAMERA ->{
                if(grantResults.isEmpty() || grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(applicationContext, "PERMISSION DENIED",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(applicationContext, "Enabled Camera Access",Toast.LENGTH_SHORT).show()
                    openCamera()
                }
            }
        }
    }
    private fun changeToPassword(){
        image_view_my_account.setOnClickListener {
            startActivity(Intent(this,UpdatePassActivity::class.java))
        }
    }
    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = "Profile "
        setSupportActionBar(toolbar)
        // give you an instance of the too lbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }



    private fun getUser(userID:String){
        var requestQueue = Volley.newRequestQueue(this)
        var stringRequest = StringRequest(Request.Method.GET,
            Config.USERS_URL + userID,
            Response.Listener {
                var gson = GsonBuilder().create()
                var user = gson.fromJson(it.toString(), User::class.java)
                profileDetails = user
                text_view_name_my_account.text = profileDetails.firstName
                text_view_email_my_account.text = profileDetails.email
                text_view_phone_my_account.text = profileDetails.mobile
            },
            Response.ErrorListener {
                Log.e("error", it.message)
            })
        requestQueue.add(stringRequest)
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
