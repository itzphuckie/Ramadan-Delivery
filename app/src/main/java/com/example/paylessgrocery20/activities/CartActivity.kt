package com.example.paylessgrocery20.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paylessgrocery20.R
import com.example.paylessgrocery20.adapters.AdapterViewCart
import com.example.paylessgrocery20.database.DBHelper
import com.example.paylessgrocery20.models.Product
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.menu_cart_layout.view.*
import kotlinx.android.synthetic.main.row_viewcart_adapter.*
import kotlinx.android.synthetic.main.row_viewcart_adapter.view.*

class CartActivity : AppCompatActivity(), AdapterViewCart.OnAdapterClickListener {

    // lateinit var navViewBttom: BottomNavigationView
    var textViewCartCount: TextView? = null
    lateinit var dbHelper: DBHelper
    lateinit var adapterCart: AdapterViewCart
    lateinit var mList: ArrayList<Product>

    val totalAmount = ""
    val ourPrice = ""
    val discount  = ""
    val orderAmount = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        dbHelper = DBHelper(this)
        //mList = dbHelper.readAllItemCart()

        setupToolbar()
        //setBottomNavigation()
        init()
        adapterCart.setOnAdapterClickListener(this)
        cart_checkout_backshopping()
        updateCartCount()
        updateUI()
        //navViewBttom.setOnNavigationItemSelectedListener(myOnNavigationItemSelectedListener)
    }

    private fun cart_checkout_backshopping() {
        button_checkout_cart.setOnClickListener {
            startActivity(Intent(this, CheckoutShippingActivity::class.java))
        }
        button_cart_to_category.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
        }
    }

    override fun onRestart() {
        //button_checkout_cart.setBackgroundColor(resources.getColor(R.color.colorAccent))
        updateCartCount()
        super.onRestart()
    }


    private fun init() {
        mList = dbHelper.readAllItemCart()
        adapterCart = AdapterViewCart(this, mList)
        recycler_view_cart.layoutManager = LinearLayoutManager(this)
        recycler_view_cart.addItemDecoration(
            DividerItemDecoration(
                recycler_view_cart.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        recycler_view_cart.adapter = adapterCart
        priceCalculation(mList)
        setupToolbar()

    }

    private fun priceCalculation(cartList: ArrayList<Product>) {
        var result: Double = 0.0
//        var resultRMP: Double = 0.0
//        var tax: Double = 0.075
        var subtotal: Double = 0.0

        //for(item in mList){
        for (item in cartList) {
            result += item.price * item.quantity
            //resultRMP += item.mrp * item.quantity
        }
        subtotal = result
//        text_view_total_price.text = "Rs. " + result.toFloat().toString()
//        text_view_total_mrp.text = "Rs. " + resultRMP.toFloat().toString()
//        text_view_tax.text = "Rs. " + tax.toFloat().toString()
//        text_view_subtotal.text = "Rs. " + subtotal.toFloat().toString()
        text_view_sub_total_cart.text =
            "Total Payment: Rs. " + (result).toFloat().toString()
        //val orderAmount = resultRMP.toFloat().toString()
        //val ourPrice = result.toFloat().toString()
        //val discount  = (resultRMP - result).toFloat().toString()
        val totalAmount = subtotal.toFloat().toString()
        var sharePref = getSharedPreferences("orderSummary", Context.MODE_PRIVATE)
        var editor = sharePref.edit()
        editor.putString("orderAmount",orderAmount)
        editor.putString("ourPrice",ourPrice)
        editor.putString("discount",discount)
        editor.putString("totalAmount",totalAmount)
        editor.apply()
    }


    private fun setupToolbar() {
        var toolbar = tool_bar
        toolbar.title = "Your Cart "
        setSupportActionBar(toolbar)
        // give you an instance of the too lbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun updateUI() {
        var count = dbHelper.getCartQuantityTotal()
        if (count > 0) {
            // cart has item
            interface_cart_empty.visibility = View.GONE
            interface_cart_view.visibility = View.VISIBLE
        } else {
            // cart is empty
            interface_cart_empty.visibility = View.VISIBLE
            interface_cart_view.visibility = View.GONE
        }
        priceCalculation(mList)
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
            startActivity(Intent(this, CartActivity::class.java))
        }
        return super.onCreateOptionsMenu(menu)
    }

    fun updateCartCount() {
        var count = dbHelper.getCartQuantityTotal()
        if (count == 0) {
            textViewCartCount?.visibility = View.GONE
        } else {
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = count.toString()
        }
        //Toast.makeText(applicationContext, ""+count, Toast.LENGTH_SHORT).show()
    }


//    override fun onItemClicked(position: Int) {
//    }

    override fun onItemClicked(position: Int, view: View) {
//        var product = mList[position]
//        var count = product.quantity
        when (view.id) {
            R.id.button_empty_product_cart -> {
//                dbHelper.deleteProduct(product)
//                mList.removeAt(position)
//                adapterCart.notifyItemRemoved(position)
                updateCartCount()
                mList = dbHelper.readAllItemCart()
                priceCalculation(mList)

            }
            R.id.button_minus_item_cart -> {
//                count -= 1
//                if (count > 0) {
//                    text_view_cart_product_quantity_cart.text = count.toString()
//                    //dbHelper.addItemCart(product)
//                    dbHelper.updateProductQuantity(product, count)
//                    mList.set(position, product)
//                    adapterCart.notifyItemChanged(position)
//
                //               }else{
//                    dbHelper.deleteProduct(product)
//                    mList.removeAt(position)
//                    adapterCart.notifyItemRemoved(position)
                //               }
                updateCartCount()
                mList = dbHelper.readAllItemCart()
                priceCalculation(mList)
            }
            R.id.button_plus_item_cart -> {
//                count += 1
//                text_view_cart_product_quantity_cart.text = count.toString()
//                mList.set(position, product)
//               // dbHelper.addItemCart(product)
//                dbHelper.updateProductQuantity(product, count)
//                adapterCart.notifyItemChanged(position)

                updateCartCount()
                mList = dbHelper.readAllItemCart()
                priceCalculation(mList)
            }
        }
        // updateCartCount()
        updateUI()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menuHome -> {
                var intent = Intent(this, CategoryActivity::class.java)
                startActivity(intent)
            }
//            R.id.menuMyAccount -> {
//                var intent = Intent(this, MyAccountActivity::class.java)
//                startActivity(intent)
//            }
//            R.id.menuHome -> {
//                var intent = Intent(this, CategoryActivity::class.java)
//                startActivity(intent)
//            }
//            R.id.menuViewCart -> {
//                var intent2cart = Intent(this, CartActivity::class.java)
//                startActivity(intent2cart)
//            }
//            R.id.menuLogOut -> {
//                var intent2cart = Intent(this, MainActivity::class.java)
//                startActivity(intent2cart)
//            }
        }
        return super.onOptionsItemSelected(item)
    }
}
