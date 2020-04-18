package com.example.paylessgrocery20.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.paylessgrocery20.fragments.CommonFragment

class AdapterFragment(fm: FragmentManager): FragmentPagerAdapter(fm) {
    private var mList: ArrayList<String> = ArrayList()
    private var mFragmentList: ArrayList<Fragment> = ArrayList()

    override fun getCount(): Int {
        return mList.size
    }


    override fun getItem(position: Int): Fragment {
        return mFragmentList.get(position)

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mList.get(position)
    }
    fun addFragment(categorizeName: String, subID:Int){
        //mFragmentList.add(CommonFragment.newInstance(categorizeName))
        mFragmentList.add(CommonFragment.newInstance(subID))
        mList.add(categorizeName)
    }
}