package com.ramadandelivery.paylessgrocery20.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadandelivery.paylessgrocery20.R
import com.ramadandelivery.paylessgrocery20.models.OrderHistory
import kotlinx.android.synthetic.main.row_order_adapter.view.*
import kotlin.collections.ArrayList

class AdapterOrder (var mContext: Context, var mList: ArrayList<OrderHistory>):
    RecyclerView.Adapter<AdapterOrder.MyViewHolder>() {


    var listner: OnAdapterClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_order_adapter, parent, false)
        var myViewHolder = MyViewHolder(view)
        return myViewHolder
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var product =  mList.get(position)
        holder.bindView(product, position)
    }

    interface OnAdapterClickListener{
        fun onItemClicked(position: Int)
    }

    fun setOnAdapterClickListner(onAdapterClickListener: OnAdapterClickListener){
        listner = onAdapterClickListener
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindView(order: OrderHistory,position: Int){
            itemView.text_view_order_ID.text = order.orderID
            itemView.text_view_order_date.text = order.orderDate
            itemView.text_view_order_est_delivery.text = order.orderDeliveryDate
            itemView.text_view_productList1.text = order.orderCart1
            itemView.text_view_productList2.text = order.orderCart2
            itemView.text_view_productList3.text = order.orderCart3
            itemView.text_view_productList4.text = order.orderCart4
            itemView.text_view_order_track_order.text = order.trackOrder

            var isClick = true
            itemView.layout_order_description.visibility = View.GONE
            itemView.setOnClickListener {
                if(isClick){
                    itemView.layout_order_description.visibility = View.VISIBLE
                    isClick = false
                }
                else{
                    itemView.layout_order_description.visibility = View.GONE
                    isClick = true
                }

            }
        }
    }

}