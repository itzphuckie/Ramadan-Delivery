package com.ramadandelivery.paylessgrocery20.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadandelivery.paylessgrocery20.R
import com.ramadandelivery.paylessgrocery20.app.Config
import com.ramadandelivery.paylessgrocery20.models.Category
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_category_adapter.view.*

class AdapterCategory(var mContext: Context, var mList: ArrayList<Category>) :
    RecyclerView.Adapter<AdapterCategory.MyViewHolder>() {
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
            LayoutInflater.from(mContext).inflate(R.layout.row_category_adapter, parent, false)
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

    fun setData(list: ArrayList<Category>) {
        mList = list
        notifyDataSetChanged()
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(category: Category, position: Int) {
            itemView.text_view_name_category.text = category.catName
            Picasso.with(mContext)
                .load(Config.IMAGE_URL + category.catImage)
                .placeholder(R.drawable.no_image_available)
                .into(itemView.image_view_category_row)

            // Listener for onClick
            itemView.setOnClickListener {
                listener?.onItemClicked(position)
            }
        }
    }
}