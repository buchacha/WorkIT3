package com.example.workit3

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter


class MyTabAdapter(private val myContext: Context, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {


    private var mFragmentList: ArrayList<Fragment> = ArrayList()
    private var mFragmentTitleList: ArrayList<String> = ArrayList()
    private var mFragmentIconList: ArrayList<Int> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return mFragmentList.get(position)
    }

    fun addFragment(
        fragment: Fragment?,
        title: String?,
        icon: Int
    ) {
        mFragmentList.add(fragment!!)
        mFragmentTitleList.add(title!!)
        mFragmentIconList.add(icon)

    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return null
    }

    override fun getCount(): Int {
        return 3
    }

    fun getTabView(position: Int, context: Context?): View? {
        val view: View = LayoutInflater.from(context).inflate(R.layout.custom_tab, null)
        val tabTextView: TextView = view.findViewById(R.id.tabTextView)
//        tabTextView.text = mFragmentTitleList[position]
        val tabImageView: ImageView = view.findViewById(R.id.tabImageView)
        tabImageView.setImageResource(mFragmentIconList[position])
        return view
    }

    fun getSelectedTabView(position: Int, context: Context?): View? {
        val view: View = LayoutInflater.from(context).inflate(R.layout.custom_tab, null)
        val tabTextView: TextView = view.findViewById(R.id.tabTextView)
//        tabTextView.text = mFragmentTitleList[position]
        tabTextView.textSize = 26f // for big text, increase text size
        tabTextView.setTextColor(ContextCompat.getColor(context!!, R.color.selectedTab))
        val tabImageView: ImageView = view.findViewById(R.id.tabImageView)
        tabImageView.setImageResource(mFragmentIconList[position])
        tabImageView.setColorFilter(
            ContextCompat.getColor(context, R.color.selectedTab),
            PorterDuff.Mode.SRC_ATOP
        )
        return view
    }
}