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
import kotlinx.android.synthetic.main.row_confirmation_product.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterCartConfirmation (var mContext: Context, var mList: ArrayList<Product>) :
    RecyclerView.Adapter<AdapterCartConfirmation.MyViewHolder>() {
    var quantityCount:Int = 0
    // Lisenter handlings
    var listener: OnAdapterClickListener ?=null
    var dbHelper = DBHelper(mContext)

    interface OnAdapterClickListener{
        fun onItemClicked(position: Int, view: View)
        //fun onItemClicked(position: Int)
    }

    fun setOnAdapterClickListener(onAdapterClickListener: OnAdapterClickListener){
        listener = onAdapterClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view =
            LayoutInflater.from(mContext).inflate(R.layout.row_confirmation_product, parent, false)
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
            itemView.text_view_product_name_cart_confirmation.text = product.productName
            itemView.text_view_product_price_cart_confirmation.text = symbol + " " + product.price.toString()
            itemView.text_view_product_mrp_cart_confirmation.paintFlags = itemView.text_view_product_mrp_cart_confirmation.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            itemView.text_view_product_mrp_cart_confirmation.text = symbol + " " + product.mrp.toString()
            itemView.text_view_product_save_cart_confirmation.text = "Save " + symbol + " " + (product.mrp - product.price).toFloat().toString()
            itemView.text_view_product_quantity_confirmation.text = "X " + product.quantity.toString()
            Picasso.with(mContext)
                .load(Config.IMAGE_URL + product.image)
                .placeholder(R.drawable.no_image_available)
                .into(itemView.image_view_product_cart_confirmation)


        }

    }
}