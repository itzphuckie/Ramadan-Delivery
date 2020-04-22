package com.example.paylessgrocery20.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.paylessgrocery20.R
import com.example.paylessgrocery20.adapters.AdapterCategory
import com.example.paylessgrocery20.app.Endpoints
import com.example.paylessgrocery20.database.DBHelper
import com.example.paylessgrocery20.models.Category
import com.example.paylessgrocery20.models.CategoryList
import com.example.paylessgrocery20.models.Product
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.menu_cart_layout.view.*

class CategoryActivity : AppCompatActivity(), AdapterCategory.OnAdapterClickListener,
    NavigationView.OnNavigationItemSelectedListener {
    lateinit var adapterCategory: AdapterCategory
    var categoryList: ArrayList<Category> = ArrayList()
    //var productList: ArrayList<Product> = ArrayList()
    lateinit var auth : FirebaseAuth
    override fun onBackPressed() {
        //super.onBackPressed()
        //finish()
    }

    var textViewCartCount: TextView? = null
    var dbHelper = DBHelper(this)
    // For navigation bar
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var navViewBttom: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        init()
        getBanner()
        getCategories()
        updateCartCount()
        setBottomNavigation()
        auth = FirebaseAuth.getInstance()
        navViewBttom.setOnNavigationItemSelectedListener(myOnNavigationItemSelectedListener)
    }

    override fun onRestart() {
        super.onRestart()
        updateCartCount()
    }

    private fun getBanner() {
        val arrayOf = arrayListOf<Int>(
            R.drawable.ram_1,
            R.drawable.ram_2,
            R.drawable.ram_3,
            R.drawable.ram_4
        )
        val names = arrayListOf<String>(
            "Ramadan Kareem  call us @ 9886258189",
            "Happy Ramadan     call us @ 9886258189",
            "call us @ 9886258189 ",
            "Stay home and shop for food !!!   call us @ 9886258189"
        )
        image_banner.setImages(arrayOf)
            .setDelayTime(5000)
            .isAutoPlay(true)
            .setBannerTitles(names)
            .setImageLoader(object : ImageLoader() {
                override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                    imageView?.setImageResource(path as Int)
                }
            })
        image_banner.setIndicatorGravity(BannerConfig.CENTER)
        image_banner.setBannerAnimation(Transformer.Default)
        image_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        image_banner.start()
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
    private fun init() {
        setupToolbar()
        //setBottomNavigation()
        adapterCategory = AdapterCategory(this, categoryList)
        recycler_view_category.layoutManager = GridLayoutManager(this, 2)
        recycler_view_category.adapter = adapterCategory
        adapterCategory.setOnAdapterClickListner(this)
    }

    override fun onItemClicked(position: Int) {
        var intent = Intent(this, SubCategoryActivity::class.java)
        intent.putExtra("Category", categoryList[position])
        Log.d("CatID",categoryList[position].toString())
        startActivity(intent)
    }


    private fun getCategories() {
        var requestQueue = Volley.newRequestQueue(this)
        var stringRequest = StringRequest(
            Request.Method.GET,
            Endpoints.getCategory(),
            Response.Listener {
                var gson = GsonBuilder().create()
                var catListsJson = gson.fromJson(it.toString(), CategoryList::class.java)
                categoryList = catListsJson.data
                adapterCategory.setData(categoryList)
                progress_bar_category.visibility = View.GONE

            },
            Response.ErrorListener {
                Log.e("error", it.message)
            })
        requestQueue.add(stringRequest)
    }

    private fun setBottomNavigation() {
        navViewBttom = findViewById(R.id.nav_bottom_view)
    }


    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Ramzan Delivery "
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    private val myOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, RCActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, MyAccountActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_cart -> {
                    startActivity(Intent(this, OrderActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_reward -> {
                    startActivity(Intent(this, RewardActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_signout -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                startActivity(Intent(this, MyAccountActivity::class.java))
            }
            R.id.nav_promotion -> {
                startActivity(Intent(this, RCActivity::class.java))
            }
            R.id.nav_view_cart -> {
                startActivity(Intent(this, OrderActivity::class.java))
            }
            R.id.nav_update -> {
                startActivity(Intent(this, UpdateProfileActivity::class.java))
            }
            R.id.nav_logout -> {
                auth.signOut()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}
