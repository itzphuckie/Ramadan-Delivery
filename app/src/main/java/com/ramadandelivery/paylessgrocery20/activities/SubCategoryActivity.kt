package com.ramadandelivery.paylessgrocery20.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.view.MenuItemCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ramadandelivery.paylessgrocery20.R
import com.ramadandelivery.paylessgrocery20.adapters.AdapterFragment
import com.ramadandelivery.paylessgrocery20.app.Endpoints
import com.ramadandelivery.paylessgrocery20.database.DBHelper
import com.ramadandelivery.paylessgrocery20.fragments.CommonFragment
import com.ramadandelivery.paylessgrocery20.models.Category
import com.ramadandelivery.paylessgrocery20.models.SubCategory
import com.ramadandelivery.paylessgrocery20.models.SubCategoryList
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.menu_cart_layout.view.*

class SubCategoryActivity : AppCompatActivity(),CommonFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction() {
        updateCartCount()
    }

    //lateinit var navViewBttom: BottomNavigationView
    lateinit var adapterSubCategory: AdapterFragment
    var mList: ArrayList<SubCategory> = ArrayList()
    var textViewCartCount:TextView? = null
    var dbHelper = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        var category = intent.getSerializableExtra("Category") as Category
        Log.d("CatID",category.catId.toString())
        var catId = category.catId
        //var catId = intent.getIntExtra("CatID",0)
        setupToolbar(catId)
        getSubcatogery(catId)
        //setBottomNavigation()
        updateCartCount()
       // navViewBttom.setOnNavigationItemSelectedListener(myOnNavigationItemSelectedListener)
        //init()

    }

    private fun getSubcatogery(catId: Int){
        catId.toString()
        var requestQueue = Volley.newRequestQueue(this)

        var stringRequest = StringRequest(
            Request.Method.GET,
            Endpoints.getSubCategoryByCatID() + catId.toString(),
            Response.Listener {
                var gson = GsonBuilder().create()
                var SubCateList = gson.fromJson(it.toString(), SubCategoryList::class.java)
                mList = SubCateList.data
                adapterSubCategory = AdapterFragment(supportFragmentManager)

                for (i in 0..mList.size-1){
                    adapterSubCategory.addFragment(mList[i].subName,mList[i].subId)
                }
                //adapterCategory.setData(mList)
                //progress_bar.visibility = View.GONE
                view_pager_sub_category.adapter = adapterSubCategory
                tab_layout_sub_category.setupWithViewPager(view_pager_sub_category)

            },
            Response.ErrorListener {
                Log.e("error", it.message)
            })
        requestQueue.add(stringRequest)
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
    override fun onRestart() {
        super.onRestart()
        updateCartCount()
        //Toast.makeText(this,"restart",Toast.LENGTH_SHORT).show()
        view_pager_sub_category.adapter=adapterSubCategory
        tab_layout_sub_category.setupWithViewPager(view_pager_sub_category)
    }

//    private fun updateCartCount() {
//        var dbHelper = DBHelper(this)
//        var count = dbHelper.getCartQuantityTotal()
//        if (count == 0) {
//            textViewCartCount?.visibility = View.GONE
//        } else {
//            textViewCartCount?.visibility = View.VISIBLE
//            textViewCartCount?.text = count.toString()
//        }
//    }
    private fun setupToolbar(catId: Int){
        var toolbar = tool_bar
        when(catId){
            1 ->{
                toolbar.title = getString(R.string.activity_subcategory_1)
            }
            2 ->{
                toolbar.title = getString(R.string.activity_subcategory_2)
            }
            3 ->{
                toolbar.title = getString(R.string.activity_subcategory_3)
            }
            4 ->{
                toolbar.title = getString(R.string.activity_subcategory_4)
            }
        }
        setSupportActionBar(toolbar)
        // give you an instance of the too lbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu, menu)
//        var item = menu.findItem(R.id.menuViewCart)
//        MenuItemCompat.setActionView(item, R.layout.meny_view_cart)
//        var view = MenuItemCompat.getActionView(item)
//        textViewCartCount = view.text_view_cart_count
//        updateCartCount()
//        view.setOnClickListener {
//            startActivity(Intent(this, CartActivity::class.java))
//        }
//        return super.onCreateOptionsMenu(menu)
 //       return true
//    }
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

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> finish()
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
//        }
//        return super.onOptionsItemSelected(item)
//    }
}
