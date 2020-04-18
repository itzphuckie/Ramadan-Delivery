package com.example.paylessgrocery20.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.paylessgrocery20.R
import com.example.paylessgrocery20.adapters.AdapterCategoryWeeklyDeal
import com.example.paylessgrocery20.app.Endpoints
import com.example.paylessgrocery20.models.Product
import com.example.paylessgrocery20.models.ProductList
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.app_bar.*

class FavoriteActivity : AppCompatActivity(), AdapterCategoryWeeklyDeal.OnAdapterClickListener {
    var animation: Animation?= null
    lateinit var adapterWeeklyDeal: AdapterCategoryWeeklyDeal
    var productList: ArrayList<Product> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        init()
        setupToolbar()
        getProduct()
        getProduct1()
        getProduct2()
        getProduct3()
        getProduct4()
        getProduct5()

        animation = AnimationUtils.loadAnimation(applicationContext,R.anim.blink)
        text_view_favorite.animation
        text_view_favorite.startAnimation(animation)
    }
    private fun init() {
        adapterWeeklyDeal = AdapterCategoryWeeklyDeal(this, productList)
        recycler_view_favorite.layoutManager = GridLayoutManager(this,2)
        recycler_view_favorite.adapter = adapterWeeklyDeal
        adapterWeeklyDeal.setOnAdapterClickListner(this)
    }
    override fun onProductClicked(position: Int) {
        var intent = Intent(this, ProductActivity::class.java)
        intent.putExtra("Product", productList.get(position))
        startActivity(intent)
    }
    private fun setupToolbar(){
        var toolbar = tool_bar
        toolbar.title = "Favorite"
        setSupportActionBar(toolbar)
        // give you an instance of the too lbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
    private fun getProduct1() {
        var requestQueue = Volley.newRequestQueue(this)
        var url = Endpoints.getProductBySubID() + "3"
        var stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener {
                //Log.d("apolis data", it.toString())
                var gson = GsonBuilder().create()
                var proList = gson.fromJson(it.toString(), ProductList::class.java)
                var product1 = proList.data.get(1)
                productList.add(product1)
                adapterWeeklyDeal.setData(productList)
            },
            Response.ErrorListener {
                Log.e("error", it.message)
            })
        requestQueue.add(stringRequest)
    }
    private fun getProduct() {
        var requestQueue = Volley.newRequestQueue(this)
        var url = Endpoints.getProductBySubID() + "2"
        var stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener {
                //Log.d("apolis data", it.toString())
                var gson = GsonBuilder().create()
                var proList = gson.fromJson(it.toString(), ProductList::class.java)
                var product1 = proList.data.get(2)
                productList.add(product1)
                adapterWeeklyDeal.setData(productList)
            },
            Response.ErrorListener {
                Log.e("error", it.message)
            })
        requestQueue.add(stringRequest)
    }
    private fun getProduct2() {
        var requestQueue = Volley.newRequestQueue(this)
        var url = Endpoints.getProductBySubID() + "1"
        var stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener {
                //Log.d("apolis data", it.toString())
                var gson = GsonBuilder().create()
                var proList = gson.fromJson(it.toString(), ProductList::class.java)
                var product1 = proList.data.get(0)
                productList.add(product1)
                adapterWeeklyDeal.setData(productList)
            },
            Response.ErrorListener {
                Log.e("error", it.message)
            })
        requestQueue.add(stringRequest)
    }
    private fun getProduct3() {
        var requestQueue = Volley.newRequestQueue(this)
        var url = Endpoints.getProductBySubID() + "1"
        var stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener {
                //Log.d("apolis data", it.toString())
                var gson = GsonBuilder().create()
                var proList = gson.fromJson(it.toString(), ProductList::class.java)
                var product1 = proList.data.get(3)
                productList.add(product1)
                adapterWeeklyDeal.setData(productList)
            },
            Response.ErrorListener {
                Log.e("error", it.message)
            })
        requestQueue.add(stringRequest)
    }
    private fun getProduct4() {
        var requestQueue = Volley.newRequestQueue(this)
        var url = Endpoints.getProductBySubID() + "2"
        var stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener {
                //Log.d("apolis data", it.toString())
                var gson = GsonBuilder().create()
                var proList = gson.fromJson(it.toString(), ProductList::class.java)
                var product1 = proList.data.get(4)
                productList.add(product1)
                adapterWeeklyDeal.setData(productList)
            },
            Response.ErrorListener {
                Log.e("error", it.message)
            })
        requestQueue.add(stringRequest)
    }
    private fun getProduct5() {
        var requestQueue = Volley.newRequestQueue(this)
        var url = Endpoints.getProductBySubID() + "2"
        var stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener {
                //Log.d("apolis data", it.toString())
                var gson = GsonBuilder().create()
                var proList = gson.fromJson(it.toString(), ProductList::class.java)
                var product1 = proList.data.get(4)
                productList.add(product1)
                adapterWeeklyDeal.setData(productList)
            },
            Response.ErrorListener {
                Log.e("error", it.message)
            })
        requestQueue.add(stringRequest)
    }

}
