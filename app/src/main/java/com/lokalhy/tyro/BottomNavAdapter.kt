package com.lokalhy.tyro

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.lokalhy.tyro.ui.PodCastDetailFragment
import com.lokalhy.tyro.ui.PodCastFragment

class BottomNavAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position)  {
            0 -> {
                val podCastFragment = PodCastFragment()
                podCastFragment
            }

            else -> {
                val podCastDetailFragment =
                    PodCastDetailFragment()
                podCastDetailFragment
            }
        }
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getCount(): Int {
        return 2
    }
}