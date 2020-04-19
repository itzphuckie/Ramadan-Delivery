package com.example.paylessgrocery20.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.example.paylessgrocery20.R
import com.example.paylessgrocery20.activities.ProductActivity
import com.example.paylessgrocery20.adapters.AdapterProduct
import com.example.paylessgrocery20.app.Endpoints
import com.example.paylessgrocery20.database.DBHelper
import com.example.paylessgrocery20.models.Product
import com.example.paylessgrocery20.models.ProductList
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_common.*
import kotlinx.android.synthetic.main.fragment_common.view.*
import kotlinx.android.synthetic.main.row_product_adapter.view.*

class CommonFragment : Fragment(), AdapterProduct.OnAdapterClickListener {
    var textViewCartCount: TextView? = null
    private lateinit var dbHelper: DBHelper
    lateinit var adapterProduct: AdapterProduct
    var productList: ArrayList<Product> = ArrayList()
    private var listener: OnFragmentInteractionListener? = null
    lateinit var option: Spinner

    // For search
    var edittextSearch:EditText ?=null
    // TODO: Rename and change types of parameters
    private var param1: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DBHelper(context!!)
        arguments?.let {
            param1 = it.getInt("SubID")
        }
    }

    private fun getProductList(subID: Int?) {
        var requestQueue = Volley.newRequestQueue(activity)
        var url = Endpoints.getProductBySubID() + subID.toString()
        var stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener {
                //Log.d("apolis data", it.toString())
                var gson = GsonBuilder().create()
                var proList = gson.fromJson(it.toString(), ProductList::class.java)

                productList = proList.data
                    //view?.drop_down_spinner_sort?.setOnItemClickListener{
                option = view?.findViewById(R.id.drop_down_spinner_sort) as Spinner
                val sortMethods = arrayOf("Price ( L - H )", "Price ( H - L)", "Name ( A - Z )")
                option.adapter = ArrayAdapter<String>(
                    activity!!,
                    android.R.layout.simple_list_item_1,
                    sortMethods
                )
                option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        when (position) {
                            0 -> {
                                productList.sortBy { it.price }
                                adapterProduct.setData(productList)
                                //Toast.makeText(activity!!,"Pirce L - H",Toast.LENGTH_SHORT).show()
                            }
                            1 -> {
                                productList.sortByDescending { it.price }
                                adapterProduct.setData(productList)
                                //Toast.makeText(activity!!,"Pirce H - L ",Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                productList.sortBy { it.productName }
                                adapterProduct.setData(productList)
                                //Toast.makeText(activity!!,"Name A - Z",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }
                //}
                //view!!.edit_text_search_fragment.setOnClickListener {

                    edittextSearch?.addTextChangedListener(object: TextWatcher{
                        override fun afterTextChanged(editable: Editable?) {
                            filter(editable.toString())
                        }
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {

                        }
                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                        }

                    })
                //}
                adapterProduct.setData(productList)
                progress_bar_common_frag.visibility = View.GONE


            },
            Response.ErrorListener {
                Log.e("error", it.message)
            })
        requestQueue.add(stringRequest)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_common, container, false)
        edittextSearch =  view?.findViewById(R.id.edit_text_search_fragment) as EditText

        // Search function implementation
//        view!!.edit_text_search_fragment.setOnClickListener {
//
//            edittextSearch?.addTextChangedListener(object: TextWatcher{
//                override fun afterTextChanged(editable: Editable?) {
//                    filter(editable.toString())
//                }
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//
//                }
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//                }
//
//            })
//            view.recycler_view_product.setHasFixedSize(true)
//            view.recycler_view_product.layoutManager = LinearLayoutManager(activity)
//            //adapterProduct = AdapterProduct(context!!, productList)
//            view.recycler_view_product.adapter = adapterProduct
//        }
        adapterProduct = AdapterProduct(context!!, productList)
        view.recycler_view_product.layoutManager = LinearLayoutManager(activity)
        view.recycler_view_product.addItemDecoration(
            DividerItemDecoration(
                view.recycler_view_product.context,
                DividerItemDecoration.VERTICAL
            )
        )
        view.recycler_view_product.adapter = adapterProduct
        //view.recycler_view_product.addItemDecoration(DividerItemDecoration(recycler_view_product.context, DividerItemDecoration.VERTICAL))
        getProductList(param1)

        adapterProduct.setOnAdapterClickListner(this)
        //listener?.onFragmentInteraction()
        dbHelper = DBHelper(context!!)

        return view
    }

     fun filter(productName:String){
         var productFilteredList: ArrayList<Product> = ArrayList()
        for(item in productList){
            if(item.productName.toLowerCase().contains(productName.toLowerCase())){
                productFilteredList.add(item)
            }
        }
         //adapterProduct = AdapterProduct(context!!, productFilteredList)
        adapterProduct.setData(productFilteredList)
    }

    fun updateCartCount() {
        var count = dbHelper.getCartQuantityTotal()
        if (count == 0) {
            textViewCartCount?.visibility = View.GONE
        } else {
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = count.toString()
        }
        // Toast.makeText(applicationContext, ""+count, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClicked(position: Int, view: View) {
        //var intent = Intent(activity, ProductActivity::class.java)
        //var quantity = productList.get(position).quantity
        //var product = productList1.get(position)
        //intent.putExtra("Product",product)
        //intent.putExtra("Product",productList.get(position))
        //startActivity(intent)
        //var product = productList[position]
        //var count = product.quantity
        when (view.id) {
//                product.quantity = 1
//                view.button_add_to_cart_product.visibility = View.GONE
//                view.interface_button_add_minus_product.visibility = View.VISIBLE
//                view.text_view_cart_product_quantity_product.text = product.quantity.toString()
//                productList.set(position, product)
//                var productToAdd = Product(product.productName,product.quantity,product.price,product.mrp, product.image)
//                dbHelper.addItemCart(productToAdd)

            R.id.button_add_to_cart_product -> {
                listener?.onFragmentInteraction()
            }
            R.id.button_minus_item_product -> {
                listener?.onFragmentInteraction()
                //updateCartCount()
//                if(count > 0){
//                    dbHelper.updateProductQuantity(product,count)
//                    productList.set(position, product)
//                    adapterProduct.notifyItemChanged(position)
//                }
//                else{
//                    dbHelper.deleteProduct(product)
//                    productList.removeAt(position)
//                    adapterProduct.notifyItemRemoved(position)
//                }
            }
            R.id.button_plus_item_product -> {
                listener?.onFragmentInteraction()
                //updateCartCount()
//                count += 1
//                productList.set(position, product)
//                //dbHelper.addItemCart(productToAdd)
//                dbHelper.updateProductQuantity(product, count)
//                adapterProduct.notifyItemChanged(position)
            }
        }
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Int) =
            CommonFragment().apply {
                arguments = Bundle().apply {
                    putInt("SubID", param1)
                }
            }
    }
}
