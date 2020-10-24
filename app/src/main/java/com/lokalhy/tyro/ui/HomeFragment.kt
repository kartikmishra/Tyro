package com.lokalhy.tyro.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.airbnb.mvrx.*
import com.lokalhy.tyro.BottomNavAdapter
import com.lokalhy.tyro.HomeVM
import com.lokalhy.tyro.NoSwipeViewPager
import com.lokalhy.tyro.R


class HomeFragment : BaseMvRxFragment() {



    lateinit var viewPager: NoSwipeViewPager
    lateinit var bottomNavAdapter: BottomNavAdapter

    private val homeVM: HomeVM by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.vp_home)
        bottomNavAdapter =
            BottomNavAdapter(childFragmentManager)
        viewPager.adapter = bottomNavAdapter
        viewPager.offscreenPageLimit = 0

        view.findViewById<ImageView>(R.id.homeBtn).setOnClickListener {
            viewPager.setCurrentItem(0,true)
            homeVM.setParam("")
            homeVM.setBaseUrl("")
        }
        homeVM.setViewPager(viewPager)
    }

    override fun invalidate() {
        withState(homeVM) {
        }
    }
}