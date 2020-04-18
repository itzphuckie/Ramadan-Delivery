package com.example.paylessgrocery20.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.paylessgrocery20.R
import com.example.paylessgrocery20.adapters.AdapterCartConfirmation
import com.example.paylessgrocery20.adapters.AdapterPayment
import com.example.paylessgrocery20.adapters.AdapterViewCart
import com.example.paylessgrocery20.app.Config
import com.example.paylessgrocery20.database.DBHelper
import com.example.paylessgrocery20.helpers.util.AppExecutors
import com.example.paylessgrocery20.models.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_checkout_confirmation.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.collections.ArrayList

class CheckoutConfirmationActivity : AppCompatActivity(){


    lateinit var appExecutors: AppExecutors

    // Firabase
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var dbHelper:DBHelper
    lateinit var adapterCart: AdapterCartConfirmation
    lateinit var adapterPaymentCard:AdapterPayment
    lateinit var mList: ArrayList<Product>
     var cardList: ArrayList<paymentCard> = ArrayList()
    lateinit var products: ArrayList<Product>
    var nameShip = ""
    var address =""
    var city=""
    var state =""
    var zipcode = ""
    var phone = ""

    // For Order Summary
    var totalAmount = ""
    var ourPrice = ""
    var discount  = ""
    var orderAmount = ""

    // For User
    var userID = ""
    var deliveryCharges ="0.00"

    lateinit var date: Date
    lateinit var currentDate:String

    object Credentials {
        // sender email
        //should be a real gmail email address
        // you may need to enable "Access for less secure apps" (allow exceptions to 2-factor authentication) in your email settings.
        const val EMAIL = "ccckitten38129@gmail.com"
        const val PASSWORD = "Mypassword1234!"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_confirmation)
        // To send email
        appExecutors = AppExecutors()

        //placeOrder()
        var sharePref = getSharedPreferences("shippingAddress", Context.MODE_PRIVATE)
        nameShip = sharePref.getString("name","").toString()
        address = sharePref.getString("address","").toString()
        state = sharePref.getString("state","").toString()
        city = sharePref.getString("city","").toString()
        zipcode = sharePref.getString("zipcode","").toString()
        phone = sharePref.getString("phone","").toString()
        text_view_address_confirmation.text = address + " " + city + " " + state + " " + zipcode
        text_view_billing_address.text = address + " " + city + " " + state + " " + zipcode

        // For Order Summary
        var sharePrefOrderSummary = getSharedPreferences("orderSummary", Context.MODE_PRIVATE)
        totalAmount = sharePrefOrderSummary.getString("totalAmount","").toString()
//        ourPrice = sharePrefOrderSummary.getString("ourPrice","").toString()
//        discount  = sharePrefOrderSummary.getString("discount","").toString()
//        orderAmount = sharePrefOrderSummary.getString("orderAmount","").toString()
//        text_view_total_price_confirm.text = "$ "+ ourPrice
//        text_view_delivery_confirm.text = deliveryCharges
//        totalAmount = (totalAmount.toDouble() + deliveryCharges.toDouble()).toFloat().toString()
//        text_view_subtotal_confirm.text ="$ "+  totalAmount

        text_view_total_save_cart_confirm.text = "Total Saving: $ " + totalAmount


        // FOr user ID
        var sharePrefUserId = getSharedPreferences("emailID", Context.MODE_PRIVATE)
        userID = sharePrefUserId.getString("email","").toString()
        var ext = "."
        userID = userID.replace(ext,"")
        date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
        currentDate = formatter.format(date)
        dbHelper = DBHelper(this)
        mList = dbHelper.readAllItemCart()
        var text = paymentCard("1231123213121212 N/A")
        cardList.add(text)
        init()
        //setCard()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("users").child(userID).child("orders")
        placeOrder()
        //clearRecords()
    }

    private fun init() {
        adapterCart = AdapterCartConfirmation(this, mList)

        recycler_view_cart_confirm.layoutManager = LinearLayoutManager(this)
        recycler_view_cart_confirm.addItemDecoration(
            DividerItemDecoration(
                recycler_view_cart_confirm.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        recycler_view_cart_confirm.adapter = adapterCart
        setupToolbar()
    }

    override fun onBackPressed() {
        startActivity(Intent(this,CategoryActivity::class.java))
        super.onBackPressed()
    }

    override fun onRestart() {
        super.onRestart()

        init()
    }
    private fun setupToolbar() {
        var toolbar = tool_bar
        toolbar.title = "Order Summary"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun placeOrder(){
        button_place_order.setOnClickListener {
            //var userID = Config.USER_ID
            var orderStatus = "Completed"
            var orderSummary = OrderSummary(totalAmount,ourPrice,discount,deliveryCharges,orderAmount)
            // var shippingAddress = intent.getSerializableExtra("ShippingAddress") as ShippingAddress
            var shippingAddress = ShippingAddress(nameShip,address,city,state,zipcode,phone)
            var payments = Payment("N/A","N/A","Completed")
            products = dbHelper.readAllItemCart()
            //var product = Product(null,"Coca Cola 2L",2,10.0, 12.0,"Coke image url")
           // var product2 = Product(null,"Minute Maid Orange",2,10.0, 12.0,"Minute Maid Orange image url")

            var emailID = userID
            Log.d("usreId", "user ID is $userID")
            var orderID = databaseReference.push().key.toString()
            //var orderID = "1"

            var data = Order(orderID,orderStatus,orderSummary,emailID,shippingAddress,payments,products,currentDate)
            //databaseReference.child(userID).setValue(data)
            databaseReference.child(orderID).setValue(data)
            //Toast.makeText(applicationContext,"Order Placed",Toast.LENGTH_SHORT).show()
            clearRecords()
            var intent = Intent(this,SplashEndActivity::class.java)
            var sharePrefUserOrderID = getSharedPreferences("orderID", Context.MODE_PRIVATE)
            var editor = sharePrefUserOrderID.edit()
            editor.putString("orderID",orderID)
            editor.apply()
            //intent.putExtra("orderID",orderID)
            sendEmail()
            startActivity(intent)
            finish()
        }
    }

    private fun clearRecords(){
        dbHelper.deleteAllItem()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_home_1 ->{
                var intent = Intent(this,CategoryActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sendEmail(){
        appExecutors.diskIO().execute {
            val props = System.getProperties()
            props.put("mail.smtp.host", "smtp.gmail.com")
            props.put("mail.smtp.socketFactory.port", "465")
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
            props.put("mail.smtp.auth", "true")
            props.put("mail.smtp.port", "465")

            val session =  Session.getInstance(props,
                object : javax.mail.Authenticator() {
                    //Authenticating the password
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(Credentials.EMAIL, Credentials.PASSWORD)
                    }
                })

            try {
                /**
                 * Receiver
                 */
                //Creating MimeMessage object
                val mm = MimeMessage(session)
                val emailId = "ccckitten@icloud.com"
                //Setting sender address
                mm.setFrom(InternetAddress(Credentials.EMAIL))
                //Adding receiver
                mm.addRecipient(
                    Message.RecipientType.TO,
                    InternetAddress(emailId))
                //Adding subject
                mm.subject = "Order For: $nameShip"
                //Adding message
                mm.setText("Order Name: " +  nameShip + "   "
                        + "Phone Number: $phone" + "   "
                        + "Shipping Address" + " $address + $city + $state + $zipcode " + "   "
                        + "Order Detail: $mList" )

                //Sending email
                Transport.send(mm)

                appExecutors.mainThread().execute {
                    //Something that should be executed on main thread.
                }

            } catch (e: MessagingException) {
                e.printStackTrace()
            }
        }
    }

}
