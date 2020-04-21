package com.example.paylessgrocery20.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paylessgrocery20.R
import com.example.paylessgrocery20.adapters.TableItemAdapter
import com.example.paylessgrocery20.fragments.MusicOptionFragment
import com.example.paylessgrocery20.models.TableItem
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
        myList.add(TableItem(1, "Fri 24", "04:33 AM", "07:15 PM"))
        myList.add(TableItem(2, "Sat 25", "04:31 AM", "07:46 PM"))
        myList.add(TableItem(3, "Sun 26", "04:29 AM", "07:48 PM"))
        myList.add(TableItem(4, "Mon 27", "04:27 AM", "07:49 PM"))
        myList.add(TableItem(5, "Tue 28", "04:26 AM", "07:50 PM"))
        myList.add(TableItem(6, "Wed 29", "04:24 AM", "07:51 PM"))
        myList.add(TableItem(7, "Thu 30", "04:22 AM", "07:52 PM"))
        myList.add(TableItem(8, "Fri 1", "04:20 AM", "07:53 PM"))
        myList.add(TableItem(9, "Sat 2", "04:19 AM", "07:54 PM"))
        myList.add(TableItem(10, "Sun 3", "04:17 AM", "07:55 PM"))
        myList.add(TableItem(11, "Mon 4", "04:15 AM", "07:56 PM"))
        myList.add(TableItem(12, "Tue 5", "04:14 AM", "07:57 PM"))
        myList.add(TableItem(13, "Wed 6", "04:12 AM", "07:58 PM"))
        myList.add(TableItem(14, "Thu 7", "04:11 AM", "08:00 PM"))
        myList.add(TableItem(15, "Fri 8", "04:09 AM", "08:01 PM"))
        myList.add(TableItem(16, "Sat 9", "04:07 AM", "08:02 PM"))
        myList.add(TableItem(17, "Sun 10", "04:06 AM", "08:03 PM"))
        myList.add(TableItem(18, "Mon 11", "04:04 AM", "08:04 PM"))
        myList.add(TableItem(19, "Tue 12", "04:03 AM", "08:05 PM"))
        myList.add(TableItem(20, "Wed 13", "04:01 AM", "08:06 PM"))
        myList.add(TableItem(21, "Thu 14", "04:00 AM", "08:07 PM"))
        myList.add(TableItem(22, "Fri 15", "03:59 AM", "08:08 PM"))
        myList.add(TableItem(23, "Sat 16", "03:57 AM", "08:09 PM"))
        myList.add(TableItem(24, "Sun 17", "03:56 AM", "08:10 PM"))
        myList.add(TableItem(25, "Mon 18", "03:55 AM", "08:11 PM"))
        myList.add(TableItem(26, "Tue 19", "03:53 AM", "08:12 PM"))
        myList.add(TableItem(27, "Wed 20", "03:52 AM", "08:13 PM"))
        myList.add(TableItem(28, "Thu 21", "03:51 AM", "08:14 PM"))
        myList.add(TableItem(29, "Fri 22", "03:50 AM", "08:15 PM"))
        myList.add(TableItem(30, "Sat 23", "03:48 AM", "08:16 PM"))

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