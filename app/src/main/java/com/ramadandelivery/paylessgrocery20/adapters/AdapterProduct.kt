package com.ramadandelivery.paylessgrocery20.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramadandelivery.paylessgrocery20.R
import com.ramadandelivery.paylessgrocery20.activities.ProductActivity
import com.ramadandelivery.paylessgrocery20.app.Config
import com.ramadandelivery.paylessgrocery20.database.DBHelper
import com.ramadandelivery.paylessgrocery20.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_product_adapter.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterProduct (var mContext: Context, var mList: ArrayList<Product>) :
    RecyclerView.Adapter<AdapterProduct.MyViewHolder>() {
    //var quantityCount:Int = 0
    // Lisenter handlings
    var listener: OnAdapterClickListener ?=null
    //var dbHelper = DBHelper(mContext)
    lateinit var dbHelper: DBHelper

    interface OnAdapterClickListener{
        fun onItemClicked(position: Int, view:View)
    }

    fun setOnAdapterClickListner(onAdapterClickListener: OnAdapterClickListener){
        listener = onAdapterClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view =
            LayoutInflater.from(mContext).inflate(R.layout.row_product_adapter, parent, false)
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
       // fun bindView(product: Product, position: Int) {
       fun bindView(product: Product, position: Int){
            var currency = Currency.getInstance("INR")
            var symbol = currency.getSymbol()
            //var quantityCount = 0
            itemView.text_view_product_name.text = product.productName
            itemView.text_view_product_price.text = symbol + " " + product.price.toString()
            //itemView.text_view_product_mrp.text = product.mrp.toString()
            itemView.text_view_product_mrp.paintFlags =
                itemView.text_view_product_mrp.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            itemView.text_view_product_mrp.text = symbol + " " + product.mrp.toString()
            itemView.text_view_product_save.text =
                "Save " + symbol + " " + (product.mrp - product.price).toFloat().toString()
            itemView.interface_button_add_minus_product.visibility = View.GONE
            Picasso.with(mContext)
                .load(Config.IMAGE_URL + product.image)
                .placeholder(R.drawable.no_image_available)
                .into(itemView.image_view_product)
            var name = product.productName
            var price = product.price
            var mrp = product.mrp
            var image = product.image
            var description = product.description
            //var description = product.description
            itemView.setOnClickListener {
                var intent = Intent(mContext, ProductActivity::class.java)
                intent.putExtra(("Product"), product)
                mContext.startActivity(intent)
                //listener?.onItemClicked(position)
            }
            dbHelper = DBHelper(mContext)
            var quantityCount = dbHelper.getItemQuantity(product)
            itemView.button_add_to_cart_product.setOnClickListener {
                quantityCount = 1
                itemView.button_add_to_cart_product.visibility = View.GONE
                itemView.interface_button_add_minus_product.visibility = View.VISIBLE
                itemView.text_view_cart_product_quantity_product.text = quantityCount.toString()
                var productToAdd = Product(null,name, quantityCount, price, mrp, image)
                dbHelper.addItemCart(productToAdd)
                listener?.onItemClicked(position, it)
            }

            itemView.button_minus_item_product.setOnClickListener {

                if (quantityCount != 0) {
                    quantityCount -= 1
                    itemView.text_view_cart_product_quantity_product.text = quantityCount.toString()
                    //var productToAdd = Product(null,name, quantityCount, price, mrp, image)
                    //dbHelper.addItemCart(productToAdd)
                    dbHelper.updateProductQuantity(product,quantityCount)
                    //notifyItemChanged(position)
                    listener?.onItemClicked(position,it)
                }
                if(quantityCount == 0){
                    dbHelper.deleteProduct(product)
                    itemView.button_add_to_cart_product.visibility = View.VISIBLE
                    itemView.interface_button_add_minus_product.visibility = View.GONE
                    //mList.removeAt(position)
                    //notifyItemRemoved(position)
                    listener?.onItemClicked(position,it)
                }
                //dbHelper.addItemCart(product)


                //listener?.onItemClicked(position, it)
            }
            itemView.button_plus_item_product.setOnClickListener {
                //var count = dbHelper.getProductQuantity(product.productName)
                //Log.d("TestCountInProduct", "The count should be 1st- "+count.toString())
                quantityCount += 1
                itemView.text_view_cart_product_quantity_product.text = quantityCount.toString()
                //var productToAdd = Product(null,name, quantityCount, price, mrp, image)
                //dbHelper.addItemCart(productToAdd)
                dbHelper.updateProductQuantity(product,quantityCount)
                //notifyItemChanged(position)
                listener?.onItemClicked(position,it)
                //dbHelper.updateItemCart(product)
                //dbHelper.updateProductQuantity(product)
                //dbHelper.addItemCart(product)
                //Log.d("TestCountInProduct", "The count should be 2nd- "+count.toString())
                //listener?.onItemClicked(position, it)
            }
           var isClick = true
            itemView.layout_product_description.visibility = View.GONE
            itemView.image_view_product.setOnClickListener {
                //val dialog = AlertDialog.Builder(mContext)
                //dialog.setMessage(description).create().show()
                itemView.text_view_product_description.text = description
                if(isClick){
                    itemView.layout_product_description.visibility = View.VISIBLE
                    isClick = false
                }
                else{
                    itemView.layout_product_description.visibility = View.GONE
                    isClick = true
                }

            }

        }

    }
}