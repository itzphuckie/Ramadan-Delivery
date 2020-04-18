package com.example.paylessgrocery20.activities

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuItemCompat
import com.example.paylessgrocery20.R
import com.example.paylessgrocery20.database.DBHelper
import com.example.paylessgrocery20.fragments.CommonFragment
import com.example.paylessgrocery20.fragments.ShareFragment
import com.example.paylessgrocery20.models.Product
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.menu_cart_layout.view.*
import java.util.*

class ProductActivity : AppCompatActivity(), ShareFragment.OnFragmentInteractionListener{


    lateinit var option: Spinner
    var animation: Animation?= null
    //var quantityCount  = 1
    var dbHelper = DBHelper(this)
    lateinit var product1:Product
    //lateinit var navViewBttom: BottomNavigationView
    var textViewCartCount: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        //var quantityCount:Int = 0
        //updateCartCount()
        var currency = Currency.getInstance("USD")
        var symbol = currency.getSymbol()
        var dbHelper = DBHelper(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        setupToolbar()
        var product = intent.getSerializableExtra("Product") as Product
        product1 = product
        var productName = product.productName
        text_view_product_name_detail_product.text = productName
        var productPrice = product.price
        text_view_product_price_detail_product.text =   symbol + " " + productPrice.toString()
        text_view_product_mrp_detail_product.paintFlags = text_view_product_mrp_detail_product.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        text_view_product_mrp_detail_product.text = symbol + " " + product.mrp.toString()
        text_view_product_save_detail_product.text = (product.mrp - product.price).toFloat().toString()
        //text_view_cart_product_quantity_detail.text = product.quantity.toString()
        //text_view_cart_product_quantity_detail.text = quantityCount.toString()
        //var count = dbHelper.getItemQuantity(product)
        //Log.d("TestCountInProduct", "The count should be 2 - "+count.toString())

        //var quantity = dbHelper.getItemQuantity(product)
        //text_view_cart_product_quantity_detail.text = quantity.toString()
        var image = product.image

        //image_view_home_product.setImageResource(image.toInt())
        var IMAGE_PATH = "http://rjtmobile.com/grocery/images/"
        Picasso.with(this)
            .load(IMAGE_PATH + image)
            .placeholder(R.drawable.ic_launcher_background) // default images, images get stored in the cached memeory after the first time
            .into(image_view_product_detail)
        updateCount(product)
        var quantityCount  = text_view_cart_product_quantity_detail.text.toString().toInt()

        if(quantityCount != 0){
            button_add_to_cart_detail_product.visibility = View.GONE
            interface_button_add_minus_detail_product.visibility = View.VISIBLE
        }
        else{
            button_add_to_cart_detail_product.visibility = View.VISIBLE
            interface_button_add_minus_detail_product.visibility = View.GONE
        }
        startAnimation()
        // For share - refer view
        //button_referal.visibility = View.VISIBLE
        shareProduct()

        button_add_to_cart_detail_product.setOnClickListener {
            quantityCount = 1
            button_add_to_cart_detail_product.visibility = View.GONE
            interface_button_add_minus_detail_product.visibility = View.VISIBLE
            text_view_cart_product_quantity_detail.text = quantityCount.toString()
            var productToAdd = Product(null,product.productName, quantityCount, product.price, product.mrp, image)
            dbHelper.addItemCart(productToAdd)
            updateCartCount()
        }
        button_plus_item_detail_product.setOnClickListener {
            quantityCount += 1
            text_view_cart_product_quantity_detail.text = quantityCount.toString()
            //dbHelper.updateProductQuantity(product,quantityCount)
            var productToAdd = Product(null,product.productName, quantityCount, product.price, product.mrp, image)
            dbHelper.addItemCart(productToAdd)
            updateCartCount()
        }
        button_minus_item_detail_product.setOnClickListener {
            if(quantityCount!= 0){
                quantityCount -= 1
            }
            if(quantityCount == 0){
                //super.onBackPressed()
                button_add_to_cart_detail_product.visibility = View.VISIBLE
                interface_button_add_minus_detail_product.visibility = View.GONE
            }
            text_view_cart_product_quantity_detail.text = quantityCount.toString()
            dbHelper.updateProductQuantity(product, quantityCount)
            updateCartCount()
        }
        image_view_product_detail.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setMessage(product.description).create().show()
        }

        // Log.d("Quantity: ",quantity.toString())


//        add_to_cart_button_detail_product.setOnClickListener {
//            //var intent2 = Intent(this, CartActivity::class.java)
//            var cartProduct = Product(productName,quantityCount,productPrice,product.mrp, image)
//            var dbHelper = DBHelper(this)
//            dbHelper.addItemCart(cartProduct)
//
//            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()
//            //intent2.putExtra("quantity",quantityCount)
//            //startActivity(intent2)
//        }

//        view_cart_button_detail_product.setOnClickListener {
//            var intent = Intent(this, CartActivity::class.java)
//            startActivity(intent)
//        }
        //setBottomNavigation()
        //navViewBttom.setOnNavigationItemSelectedListener(myOnNavigationItemSelectedListener)
    }
    override fun onFragmentInteraction() {
        button_referal.visibility = View.VISIBLE
        fragment_container_share.visibility = View.GONE
        layout_bottom_share.visibility = View.VISIBLE
        animation = AnimationUtils.loadAnimation(applicationContext,R.anim.slide_down)
        fragment_container_share.animation
        fragment_container_share.startAnimation(animation)
    }
    private fun shareProduct(){
        // For share - refer view
        button_referal.visibility = View.VISIBLE
        //drop_down_spinner_refer.visibility = View.GONE
        // Share fragment
        fragment_container_share.visibility = View.GONE
        button_referal.setOnClickListener {
            fragment_container_share.visibility = View.VISIBLE
            layout_bottom_share.visibility = View.GONE
            animation = AnimationUtils.loadAnimation(applicationContext,R.anim.slide_up)
            fragment_container_share.animation
            fragment_container_share.startAnimation(animation)
            supportFragmentManager.beginTransaction().add(R.id.fragment_container_share, ShareFragment()).commit()
//            button_referal.visibility = View.GONE
//            drop_down_spinner_refer.visibility = View.VISIBLE
//            option = findViewById(R.id.drop_down_spinner_refer) as Spinner
//            val shareMethods = arrayOf("Facebook","TextMessage","Email","SnapChat","Instagram")
//            option.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,shareMethods)
//            option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//
//                }
//
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
//
//
//                }
//
//            }
        }

    }

    override fun onPause() {
        super.onPause()
        Log.d("test1","Product Paused")
    }

    override fun onStop() {
        super.onStop()
        Log.d("test1","Product Stopped")
    }
    private fun startAnimation(){
        animation = AnimationUtils.loadAnimation(applicationContext,R.anim.blink)
        text_view_product_save_detail_product.animation
        text_view_product_save_detail_product.startAnimation(animation)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        //return true
        var item = menu?.findItem(R.id.menuViewCart)
        MenuItemCompat.setActionView(item, R.layout.menu_cart_layout)
        var view = MenuItemCompat.getActionView(item)
        textViewCartCount = view.text_view_cart_count
        updateCartCount()
        view.setOnClickListener {
            startActivity(Intent(this,CartActivity::class.java))
        }
        return super.onCreateOptionsMenu(menu)
    }
    fun updateCartCount(){
        var count = dbHelper.getCartQuantityTotal()
        if(count ==0){
            textViewCartCount?.visibility = View.GONE
        }else{
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = count.toString()
        }
        //Toast.makeText(applicationContext, ""+count, Toast.LENGTH_SHORT).show()
    }
//    private fun setBottomNavigation(){
//        navViewBttom = findViewById(R.id.nav_bottom_view)
//    }
//    private val myOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{ item ->
//        when (item.itemId) {
//            R.id.navigation_home ->{
//                startActivity(Intent(this,CategoryActivity::class.java))
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_profile -> {
//                startActivity(Intent(this,MyAccountActivity::class.java))
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_cart -> {
//                startActivity(Intent(this,CartActivity::class.java))
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_reward -> {
//                startActivity(Intent(this,RewardActivity::class.java))
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_signout -> {
//                startActivity(Intent(this,MainActivity::class.java))
//                return@OnNavigationItemSelectedListener true
//            }
//        }
//        false
//    }
    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = "Product Detail"
        setSupportActionBar(toolbar)
        // give you an instance of the too lbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu, menu)
//        return true
//    }
    private fun updateCount(product: Product):Int{
        text_view_cart_product_quantity_detail.text = dbHelper.getItemQuantity(product).toString()
        return text_view_cart_product_quantity_detail.text.toString().toInt()
    }
    override fun onRestart() {
        super.onRestart()
        updateCount(product1)
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
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

