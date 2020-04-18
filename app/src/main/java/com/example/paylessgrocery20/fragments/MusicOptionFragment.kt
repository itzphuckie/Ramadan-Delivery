package com.example.paylessgrocery20.fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.example.paylessgrocery20.R
import com.example.paylessgrocery20.activities.DefaultMusicActivity
import com.example.paylessgrocery20.activities.RecorderActivity
import kotlinx.android.synthetic.main.fragment_music_options.view.*

/**
 * A simple [Fragment] subclass.
 */
class MusicOptionFragment : DialogFragment() {

    private var type:String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_music_options, container, false)



        view.play_default_music.setOnClickListener {
            //service
            var intent2 = Intent(activity, DefaultMusicActivity::class.java)
            intent2.putExtra("type",type)
            startActivity(intent2)
            this@MusicOptionFragment.dismiss()

        }

        view.record_new_music.setOnClickListener {
            var intent1 = Intent(activity, RecorderActivity::class.java)
            intent1.putExtra("type",type)
            startActivity(intent1)
            this@MusicOptionFragment.dismiss()
        }
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString("type")

        }
    }




    companion object{
        @JvmStatic
        fun newInstance(type: String) =
            MusicOptionFragment().apply {
                arguments = Bundle().apply {
                    putString("type", type)
                }
            }


    }


}