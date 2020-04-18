package com.example.paylessgrocery20.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paylessgrocery20.R
import com.example.paylessgrocery20.models.paymentCard
import kotlinx.android.synthetic.main.activity_checkout_confirmation.view.*
import kotlinx.android.synthetic.main.row_payment_adapter.view.*

class AdapterPayment(var mContext: Context, var mList: ArrayList<paymentCard>) :
    RecyclerView.Adapter<AdapterPayment.MyViewHolder>() {
    // Lisenter handlings
    var listener: OnAdapterClickListener ?=null

    interface OnAdapterClickListener{
        fun onItemClicked(position: Int)
    }

    fun setOnAdapterClickListner(onAdapterClickListener: OnAdapterClickListener){
        listener = onAdapterClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view =
            LayoutInflater.from(mContext).inflate(R.layout.row_payment_adapter, parent, false)
        var myViewHolder = MyViewHolder(view)
        return myViewHolder
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var category = mList.get(position)
        holder.bindView(category, position)
    }

    fun setData(list: ArrayList<paymentCard>) {
        mList = list
        notifyDataSetChanged()
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(category: paymentCard, position: Int) {
            itemView.check_box_old_card.text = category.cardNum
            itemView.setOnClickListener {
                listener?.onItemClicked(position)
            }
        }
    }
}