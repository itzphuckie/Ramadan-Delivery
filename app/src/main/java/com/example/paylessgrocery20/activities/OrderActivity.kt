package com.example.paylessgrocery20.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paylessgrocery20.R
import com.example.paylessgrocery20.adapters.AdapterOrder
import com.example.paylessgrocery20.app.Config
import com.example.paylessgrocery20.models.Order
import com.example.paylessgrocery20.models.OrderHistory
import com.example.paylessgrocery20.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.app_bar.*

class OrderActivity : AppCompatActivity() {
    internal var order: Order ?= null
    // Firabase
    var mList: ArrayList<OrderHistory> = ArrayList()
    lateinit var orderList :  ArrayList<Order>
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference


    var orderID = ""
    var orderDate = ""
    var orderStatus = ""
    var orderAdress = ""
    var userID = ""
    var orderName = ""
    var orderProductName = ""
    var orderProductQty = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        //orderList = arrayListOf()

        var sharepref = getSharedPreferences("emailID", Context.MODE_PRIVATE)
        userID = sharepref.getString("email","").toString()
        var userID2 = userID
        var ext = "."
        userID2 = userID2.replace(ext,"")
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("users").child(userID2).child("orders")
       // readOrders()
        databaseReference.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(dataSnapshot: DatabaseError) {

            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //datasnapshots = user nodes
                if(dataSnapshot.exists()){
                    for(keyNode in dataSnapshot.children){
                        var user = keyNode.getValue(Order::class.java)
                        if(user!!.user == userID){
                            orderList.add(user)
                            //orderID = user.orderID
                            for(i in orderList){
                                orderID = i.orderID
                                orderDate = i.date
                                orderStatus = i.orderStatus
                                orderName = i.shippingAddress!!.shippingName
                                orderAdress = i.shippingAddress!!.address + " " + i.shippingAddress!!.city + " " + i.shippingAddress!!.state + " " + i.shippingAddress!!.zipcode + ". " +
                                        mList.add(OrderHistory("Order Number : " + orderID,"Order Date : " + orderDate,"Expected Delivery Date: 3/01/2020","Ship to : " + orderName,"Address : " + orderAdress,
                                            "Minute Maid Orange Juice 3.4 Oz x 1","Watermelon x 2","Tracking Number : " + orderID))
                            }
                        }
                    }
                }
            }

        })
        init()

        setupToolbar()
        //var ref = FirebaseDatabase.getInstance().getReference("orders").child(FirebaseAuth.getInstance().currentUser!!.uid)
        //var ref = databaseReference.getInstance().getReference("orders")
//        val data = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                order =  dataSnapshot.getValue(Order::class.java)
//                text_view_orders.text = order?.toString()
//            }
//            override fun onCancelled(databaseError: DatabaseError) {
//                // handle error
//            }
//        }
//        databaseReference.addListenerForSingleValueEvent(object:ValueEventListener{
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if(dataSnapshot!!.exists()){
//                    for(node in dataSnapshot.children){
//                        val order = node.getValue(Order::class.java)
//                        orderList.add(order!!)
//                    }
//                }
//            }
//
//
//        })
//        recycler_view_order_history.layoutManager = LinearLayoutManager(this)
//        var adapterProduct = AdapterOrder(this, orderList)
//        recycler_view_order_history.adapter = adapterProduct
    }
    private fun readOrders(){

    }
    private fun init(){
//        for(i in orderList){
//            orderID = i.orderID
//            orderDate = i.date
//            orderStatus = i.orderStatus
//            orderName = i.shippingAddress!!.shippingName
//            orderAdress = i.shippingAddress!!.address + " " + i.shippingAddress!!.city + " " + i.shippingAddress!!.state + " " + i.shippingAddress!!.zipcode + ". " +
//                    mList.add(OrderHistory("Order Number : " + orderID,"Order Date : " + orderDate,"Expected Delivery Date: 3/01/2020","Ship to : " + orderName,"Address : " + orderAdress,
//                        "Minute Maid Orange Juice 3.4 Oz x 1","Watermelon x 2","Tracking Number : " + orderID))
//        }

        mList.add(OrderHistory("Order Number: 12D1SDS1212","Order Date: 2/23/2020","Expected Delivery Date: 3/12/2020","Minuted Maid Orange Juice x 2","Starbuck Coffee 1.5 OZ x 2",
            "Minute Maid Orange Juice 3.4 Oz x 1","Watermelon x 2","Shipping Address: 141 Covington Ct, St. Charles, IL 11211"))
        mList.add(OrderHistory("Order Number: 2121SOMDWEKMDKM1","Order Date: 2/22/2020","Expected Delivery Date: 3/01/2020","Coca-Cola 2L Bottle x 3","Starbuck Coffee 1.5 OZ x 2",
            "Minute Maid Orange Juice 3.4 Oz x 1","Watermelon x 2","Shipping Address: 141 Covington Ct, St. Charles, IL 11211"))
        recycler_view_order_history.layoutManager = LinearLayoutManager(this)
        mList.add(OrderHistory("Order Number: DAUHEU12112SDAFDAF","Order Date: 2/22/2020","Expected Delivery Date: 3/01/2020","Coca-Cola 2L Bottle x 3","Starbuck Coffee 1.5 OZ x 2",
            "Minute Maid Orange Juice 3.4 Oz x 1","Watermelon x 2","Shipping Address: 141 Covington Ct, St. Charles, IL 11211"))
        recycler_view_order_history.layoutManager = LinearLayoutManager(this)
        var adapterProduct = AdapterOrder(this, mList)
        recycler_view_order_history.adapter = adapterProduct

    }
    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = " "
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
