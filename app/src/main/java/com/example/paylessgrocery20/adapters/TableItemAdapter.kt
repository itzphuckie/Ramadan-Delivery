package com.example.paylessgrocery20.adapters

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paylessgrocery20.R
import com.example.paylessgrocery20.models.TableItem
import kotlinx.android.synthetic.main.table_item_layout.view.*

class TableItemAdapter(var mContext: Context, var myList: ArrayList<TableItem>) :
    RecyclerView.Adapter<TableItemAdapter.myViewHolder>() {


//    var mydb = Room.databaseBuilder(mContext,MyDatabase::class.java,"my_database")
//        .fallbackToDestructiveMigration()
//        .allowMainThreadQueries()
//        .build()

    // var listener:ViewPager.OnAdapterChangeListener?= null
    lateinit var pendingIntent: PendingIntent
    lateinit var alarmManager: AlarmManager
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.table_item_layout, parent, false)
        var myViewHolder = myViewHolder(view)
        return myViewHolder

    }

    override fun getItemCount(): Int {
        return myList.size

    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        var tableItem = myList.get(position)
        holder.bindView(tableItem,position)
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: TableItem, position: Int) {
            itemView.sequence_number_text.text = item.sequenceNumber.toString()
            itemView.date_text.text = item.date
            itemView.sehr_text.text = item.sehr
            itemView.iftar_text.text = item.iftar


            // var selected = mydb.myDao().readChecked(position)!=null
            var selected = false
            if(selected == false){
                itemView.setBackgroundResource(R.color.tableNormal)
            }else{
                itemView.setBackgroundResource(R.color.tableSelected)
            }

        }

    }

}