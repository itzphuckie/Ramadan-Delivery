package com.example.paylessgrocery20.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

import com.example.paylessgrocery20.R
import kotlinx.android.synthetic.main.fragment_share.view.*

/**
 * A simple [Fragment] subclass.
 */
class ShareFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    lateinit var option: Spinner
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_share, container, false)
        option = view.findViewById(R.id.drop_down_spinner_refer) as Spinner
            val shareMethods = arrayOf("Facebook","TextMessage","Email","SnapChat","Instagram")
            option.adapter = ArrayAdapter<String>(activity!!,android.R.layout.simple_list_item_1,shareMethods)
            option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                }

            }
        view.button_share_send.setOnClickListener {
            listener?.onFragmentInteraction()
        }
        return view
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
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction()
    }

}
