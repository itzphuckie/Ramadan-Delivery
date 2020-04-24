package com.ramadandelivery.paylessgrocery20.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadandelivery.paylessgrocery20.R
import com.ramadandelivery.paylessgrocery20.app.Config
import com.ramadandelivery.paylessgrocery20.database.DBHelper
import com.ramadandelivery.paylessgrocery20.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_weekly_deal_adapter.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterCategoryWeeklyDeal (var mContext: Context, var mList: ArrayList<Product>) :
    RecyclerView.Adapter<AdapterCategoryWeeklyDeal.MyViewHolder>() {
    //var quantityCount:Int = 0
    // Lisenter handlings
    var listener: OnAdapterClickListener ?=null
    var dbHelper = DBHelper(mContext)
    interface OnAdapterClickListener{
        fun onProductClicked(position: Int)
    }

    fun setOnAdapterClickListner(onAdapterClickListener: OnAdapterClickListener){
        listener = onAdapterClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view =
            LayoutInflater.from(mContext).inflate(R.layout.row_weekly_deal_adapter, parent, false)
        var myViewHolder = MyViewHolder(view)
        return myViewHolder
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var product = mList.get(position)
        holder.bindView(product, position)
    }

    fun setData(list: ArrayList<Product>) {
        mList = list
        notifyDataSetChanged()
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(product: Product, position: Int) {
            var currency = Currency.getInstance("INR")
            var symbol = currency.getSymbol()
            itemView.text_view_price_weekly_deal.text = symbol + " " + product.price.toString()
            itemView.text_view_rmp_weekly_deal.paintFlags = itemView.text_view_rmp_weekly_deal.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            itemView.text_view_rmp_weekly_deal.text = symbol + " " + product.mrp.toString()
            itemView.text_view_name_category_weekly_deal.text = product.productName
            Picasso.with(mContext)
                .load(Config.IMAGE_URL + product.image)
                .placeholder(R.drawable.no_image_available)
                .into(itemView.image_view_category_weekly_deal)
            itemView.setOnClickListener {
                listener?.onProductClicked(position)
            }
        }
    }

}