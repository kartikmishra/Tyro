package com.lokalhy.tyro.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.mvrx.*
import com.lokalhy.tyro.HomeVM
import com.lokalhy.tyro.R
import com.lokalhy.tyro.model.PodCast
import com.lokalhy.tyro.epoxyviews.podCastAuthorView


class PodCastFragment : BaseMvRxFragment() {


    private val homeVm: HomeVM by activityViewModel()
    lateinit var rv_podcasts: EpoxyRecyclerView
    val podCastEpoxyController = PodCastEpoxyController()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pod_cast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_podcasts = view.findViewById(R.id.rv_podcasts)

        val authorList = ArrayList<PodCast>()
        val joeRogan = PodCast(
            "The Joe Rogan Experience",
            "http://static.libsyn.com/p/assets/7/1/f/3/71f3014e14ef2722/JREiTunesImage2.jpg",
                "http://joeroganexp.joerogan.libsynpro.com/","rss"
        )
        val naval = PodCast(
            "Naval",
            "https://ssl-static.libsyn.com/p/assets/5/c/e/b/5ceb9fba4ff0ab14/podcast_artwork.jpg",
                "https://naval.libsyn.com/","rss"
        )
        val benShapiro = PodCast(
            "The Ben Shapiro Show",
            "https://images.megaphone.fm/_IaIovXFJT1vbHkbxi3QmrIR4mASd3rp156Qb26WJ2c/plain/s3://megaphone-prod/podcasts/35b42868-5c97-11ea-b0cc-039c766dfa49/image/avatars-000703722727-g9mf5u-original.jpg",
                "https://feeds.megaphone.fm/","WWO8086402096"
        )
        val theDaily = PodCast(
            "The Daily",
            "https://content.production.cdn.art19.com/images/01/1b/f3/d6/011bf3d6-a448-4533-967b-e2f19e376480/c81936f538106550b804e7e4fe2c236319bab7fba37941a6e8f7e5c3d3048b88fc5b2182fb790f7d446bdc820406456c94287f245db89d8656c105d5511ec3de.jpeg",
                "http://rss.art19.com/","the-daily"
        )

        val dateLineNbc = PodCast(
                "Dateline NBC",
                "https://content.production.cdn.art19.com/images/81/8f/a0/6a/818fa06a-b573-43c9-a870-fef30e9cac5e/7f0421f73d2ce0ca272e392c937e1a301285d44fe7c6d710c2844d80c0c7bb1a3e9838ac03ee80fc64199891cb9d5c6e9d4490f5081fb379c0ab2317f2cadf14.jpeg",
                "https://podcastfeeds.nbcnews.com/","dateline-nbc"
        )

        authorList.add(joeRogan)
        authorList.add(naval)
        authorList.add(benShapiro)
        authorList.add(theDaily)
        authorList.add(dateLineNbc)

        val layoutManager = GridLayoutManager(requireActivity(),2)
        podCastEpoxyController.spanCount = 2
        layoutManager.spanSizeLookup = podCastEpoxyController.spanSizeLookup
        rv_podcasts.layoutManager = layoutManager
        podCastEpoxyController.setNavControllerFragment(findNavController())
        podCastEpoxyController.setData(authorList)
        rv_podcasts.setController(podCastEpoxyController)
    }

    override fun invalidate() {
        withState(homeVm) {

            when (it.channel) {
                is Success -> {
                    Log.d("Channel", "${it.channel.invoke().channel.url}}")
                }
                is Fail -> {
                    Log.d("Channel", "Failed")
                }
                else -> {}
            }
        }
    }

    inner class PodCastEpoxyController: TypedEpoxyController<List<PodCast>>() {


        lateinit var navController: NavController

        fun setNavControllerFragment(navController: NavController) {
            this.navController = navController
        }

        override fun buildModels(data: List<PodCast>) {
            data.forEach {
                podCastAuthorView {
                    id(it.title)
                    authorImage(it.image)
                    authorName(it.title)
                    spanSizeOverride { _, _, _ -> 1 }
                    onClick {
                        homeVm.setBaseUrl(it.url)
                        homeVm.setParam(it.param)
                        withState(homeVm) {
                            it.viewPager!!.setCurrentItem(1,true)
                        }
                    }
                }
            }
        }
    }

}