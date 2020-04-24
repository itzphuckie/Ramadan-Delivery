package com.ramadandelivery.paylessgrocery20.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ramadandelivery.paylessgrocery20.R
import com.ramadandelivery.paylessgrocery20.app.Config
import com.ramadandelivery.paylessgrocery20.database.DBHelper
import com.ramadandelivery.paylessgrocery20.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_viewcart_adapter.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterViewCart (var mContext: Context, var mList: ArrayList<Product>) :
    RecyclerView.Adapter<AdapterViewCart.MyViewHolder>() {
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
            LayoutInflater.from(mContext).inflate(R.layout.row_viewcart_adapter, parent, false)
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

            itemView.text_view_product_name_cart.text = product.productName
            itemView.text_view_product_price_cart.text = symbol + " " + product.price.toString()

            Picasso.with(mContext)
                .load(Config.IMAGE_URL + product.image)
                .placeholder(R.drawable.no_image_available)
                .into(itemView.image_view_product_cart)

            itemView.button_empty_product_cart.setOnClickListener {
                dbHelper = DBHelper(mContext)
                dbHelper.deleteProduct(product)
                Toast.makeText(mContext, "Deleted from Cart", Toast.LENGTH_SHORT).show()

                mList.removeAt(position)
                notifyItemRemoved(position)
                listener?.onItemClicked(position,it)
            }
            itemView.text_view_cart_product_quantity_cart.text = dbHelper.getItemQuantity(product).toString()
            var count = itemView.text_view_cart_product_quantity_cart.text.toString().toInt()
            itemView.text_view_product_total_cart.text = "Item Total: " + symbol + " " + (product.price * count).toFloat().toString()

            itemView.button_plus_item_cart.setOnClickListener{
                count += 1
                itemView.text_view_cart_product_quantity_cart.text = count.toString()
                //product.quantity = count
                dbHelper.updateProductQuantity(product,count)
                itemView.text_view_product_total_cart.text = "Item Total: "+ symbol + " " + (product.price * product.quantity).toFloat().toString()
                notifyItemChanged(position)
                listener?.onItemClicked(position,it)
            }
            itemView.button_minus_item_cart.setOnClickListener {
               // listener?.onItemClicked(position,it)
                if(count!= 0){
                    count -= 1
                    //product.quantity = count
                    itemView.text_view_cart_product_quantity_cart.text = count.toString()

                    itemView.text_view_product_total_cart.text =  "Item Total: "+symbol + " " + (product.price * count).toFloat().toString()
                    dbHelper.updateProductQuantity(product,count)
                    notifyItemChanged(position)
                }
                if(count == 0){
                    dbHelper.deleteProduct(product)
                    //dbHelper.readAllItemCart()
                    //startActivity(mContext,Intent(mContext,CartActivity::class.java),null)
                    mList.removeAt(position)
                    //itemView.text_view_product_total_cart.text = "Save " + symbol + " " + (product.price * product.quantity).toFloat().toString()
                    notifyItemRemoved(position)
                }
                listener?.onItemClicked(position,it)
            }
        }

    }
}