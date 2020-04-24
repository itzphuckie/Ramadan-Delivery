package com.ramadandelivery.paylessgrocery20.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramadandelivery.paylessgrocery20.R
import com.ramadandelivery.paylessgrocery20.adapters.TableItemAdapter
import com.ramadandelivery.paylessgrocery20.fragments.MusicOptionFragment
import com.ramadandelivery.paylessgrocery20.models.TableItem
import com.yyz.webview_3_20.itemdecoration.SpacesItemDecoration
import kotlinx.android.synthetic.main.activity_r_c.*
import kotlinx.android.synthetic.main.app_bar.*

class RCActivity : AppCompatActivity() {
    //ad
    val OPEN_AD_BROSWER = 200
    lateinit var webView: WebView
    lateinit var url: String
//    lateinit var myBrowser: MyBrowser

    //title
    lateinit var sehr: TextView
    lateinit var iftar: TextView

    //table
    lateinit var tableItemAdapter: TableItemAdapter
    var myList: ArrayList<TableItem> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_r_c)

        //setWebView()

        setToolBar()

        setTable()

        setSehrPlayer()

        setIftarPlayer()


    }

    private fun setIftarPlayer() {
        iftar = iftar_title

        iftar.setOnClickListener {
            setMedia("iftar")
        }
    }

    private fun setSehrPlayer() {
        sehr = sehr_title

        sehr.setOnClickListener {
            setMedia("sehr")


        }

    }

    private fun setMedia(type:String) {

        var fm = supportFragmentManager
        val musicOptionFragment = MusicOptionFragment.newInstance(type)
        musicOptionFragment.show(fm,"musicoption")


    }




    private fun setTable() {
        //set myList?
        //get api?
//
//        myList.add(TableItem(1, "Fri 24", "04:58 AM", "05:34PM"))
//        myList.add(TableItem(2, "Sat 25", "04:30 AM", "07:43PM"))
//        myList.add(TableItem(3, "Sun 26", "04:32 AM", "07:42PM"))
//        myList.add(TableItem(4, "Mon 27", "04:30 AM", "07:43PM"))
//        myList.add(TableItem(5, "Tue 28", "04:32 AM", "07:42PM"))
//        myList.add(TableItem(6, "Wed 29", "04:30 AM", "07:43PM"))
//        myList.add(TableItem(7, "Thu 30", "04:32 AM", "07:42PM"))
//        myList.add(TableItem(8, "Fri 1", "04:30 AM", "07:43PM"))
//        myList.add(TableItem(9, "Sat 2", "04:32 AM", "07:42PM"))
//        myList.add(TableItem(10, "Sun 3", "04:30 AM", "07:43PM"))
        myList.add(TableItem(1, "Fri 24", "04:49 AM", "6:34 PM"))
        myList.add(TableItem(2, "Sat 25", "04:48 AM", "06:35 PM"))
        myList.add(TableItem(3, "Sun 26", "04:47 AM", "06:35 PM"))
        myList.add(TableItem(4, "Mon 27", "04:47 AM", "06:35 PM"))
        myList.add(TableItem(5, "Tue 28", "04:46 AM", "06:35 PM"))
        myList.add(TableItem(6, "Wed 29", "04:45 AM", "06:35 PM"))
        myList.add(TableItem(7, "Thu 30", "04:45 AM", "06:35 PM"))
        myList.add(TableItem (8, "Fri 1", "04:44 AM", "06:36 PM"))
        myList.add(TableItem(9, "Sat 2", "04:44 AM",  "06:36 PM"))
        myList.add(TableItem(10, "Sun 3", "04:43 AM", "06:36 PM"))
        myList.add(TableItem(11, "Mon 4", "04:43 AM", "06:36 PM"))
        myList.add(TableItem(12, "Tue 5", "04:42 AM", "06:36 PM"))
        myList.add(TableItem(13, "Wed 6", "04:42 AM", "06:36 PM"))
        myList.add(TableItem(14, "Thu 7", "04:41 AM", "06:36 PM"))
        myList.add(TableItem(15, "Fri 8", "04:41 AM", "06:36 PM"))
        myList.add(TableItem(16, "Sat 9", "04:40 AM", "06:36 PM"))
        myList.add(TableItem(17, "Sun 10", "04:40 AM", "06:36 PM"))
        myList.add(TableItem(18, "Mon 11", "04:39 AM", "06:38 PM"))
        myList.add(TableItem(19, "Tue 12", "04:39 AM", "06:38 PM"))
        myList.add(TableItem(20, "Wed 13", "04:38 AM", "06:38 PM"))
        myList.add(TableItem(21, "Thu 14", "04:38 AM", "06:38 PM"))
        myList.add(TableItem(22, "Fri 15", "03:38 AM", "06:39 PM"))
        myList.add(TableItem(23, "Sat 16", "03:37 AM", "06:39 PM"))
        myList.add(TableItem(24, "Sun 17", "03:37 AM", "06:39 PM"))
        myList.add(TableItem(25, "Mon 18", "04:36 AM", "06:39 PM"))
        myList.add(TableItem(26, "Tue 19", "04:36 AM", "06:40 PM"))
        myList.add(TableItem(27, "Wed 20", "04:36 AM", "06:40 PM"))
        myList.add(TableItem(28, "Thu 21", "04:35 AM", "06:40 PM"))
        myList.add(TableItem(29, "Fri 22", "04:35 AM", "06:41 PM"))
        myList.add(TableItem(30, "Sat 23", "04:35 AM", "06:41 PM"))

        tableItemAdapter = TableItemAdapter(this, myList)
        table_items_recyclerview.layoutManager = LinearLayoutManager(this)
        table_items_recyclerview.adapter = tableItemAdapter
        table_items_recyclerview.addItemDecoration(SpacesItemDecoration())

    }

    private fun setToolBar() {
        var toolbar: androidx.appcompat.widget.Toolbar = tool_bar
        toolbar.title = "Ramadan Calendar"
        setSupportActionBar(toolbar)
    }

//    private fun setWebView() {
//        webView = findViewById(R.id.webview)
//        url = "https://giphy.com/"
//        myBrowser = MyBrowser()
//        myBrowser.shouldOverrideUrlLoading(webView, url)
//        webView.webViewClient = myBrowser
//
//
//        detail_button.setOnClickListener {
//            //Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show()
//            var uri = Uri.parse(url)
//            //startActivity(Intent(Intent.ACTION_VIEW,uri))
//            startActivityForResult(Intent(Intent.ACTION_VIEW, uri), OPEN_AD_BROSWER)
//        }
//
//    }
}