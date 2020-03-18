package com.example.workit3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var adapter: MyTabAdapter? = null

    var mMyFragment: EmployerFragment? = null

    private val tabIcons = intArrayOf(
        R.drawable.hr,
        R.drawable.dev,
        R.drawable.man
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            mMyFragment = getSupportFragmentManager().getFragment(savedInstanceState, "myFragmentName") as EmployerFragment
        }

        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager = findViewById<ViewPager>(R.id.viewPager)

        tabLayout?.setupWithViewPager(viewPager);

        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        adapter = MyTabAdapter(this, supportFragmentManager)

        adapter?.addFragment(HRFragment(), "HR", tabIcons[0])
        adapter?.addFragment(EmployeeFragment(), "Employee", tabIcons[1])
        adapter?.addFragment(EmployerFragment(), "Employer", tabIcons[2])

        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        highLightCurrentTab(0);

        viewPager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                highLightCurrentTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

    }

    private fun highLightCurrentTab(position: Int) {
        for (i in 0 until tabLayout!!.getTabCount()) {
            val tab: TabLayout.Tab = tabLayout?.getTabAt(i)!!
            tab.setCustomView(null)
            tab.setCustomView(adapter?.getTabView(i, this))
        }
        val tab: TabLayout.Tab = tabLayout?.getTabAt(position)!!
        tab.setCustomView(null)
        tab.setCustomView(adapter?.getSelectedTabView(position, this))
    }
}
