package com.lokalhy.tyro.ui

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.*
import com.google.android.material.textfield.TextInputEditText
import com.lokalhy.tyro.HomeState
import com.lokalhy.tyro.HomeVM
import com.lokalhy.tyro.R
import com.lokalhy.tyro.base.simpleController
import com.lokalhy.tyro.epoxyviews.loadingView
import com.lokalhy.tyro.epoxyviews.podCastDetailTopView
import com.lokalhy.tyro.epoxyviews.podCastView
import com.lokalhy.tyro.epoxyviews.searchBoxView

class PodCastDetailFragment : BaseMvRxFragment() {


    private val homeVm: HomeVM by activityViewModel()
    lateinit var rv_podcast: EpoxyRecyclerView
    private val controller: EpoxyController by lazy { buildController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        findNavController().navigateUp()
                        withState(homeVm) {
                            it.viewPager!!.setCurrentItem(0,true)
                        }
                    }
                })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pod_cast_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_podcast = view.findViewById(R.id.rv_podcast)
        rv_podcast.setController(controller)


        homeVm.selectSubscribe(HomeState::url, HomeState::param,uniqueOnly()) { url , param ->
            homeVm.getPodCasts()
        }
    }


    private fun buildController()  = simpleController(homeVm) { state->
        showPodCastDetail(state)
    }

    private fun EpoxyController.showPodCastDetail(state: HomeState) {
        when(state.channel) {
            is Loading -> {
                loadingView {
                    id("loading")
                    message("Loading Your PodCast...")
                }
            }

            is Success -> {
                var channel = state.channel.invoke().channel
                homeVm.setItemList(channel.item!!)
                podCastDetailTopView {
                    id("topview")
                    title(channel.title)
                    description(channel.description)
                    if(channel.url != null) {
                        authorImage(channel.url)
                     } else {
                        authorImage("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.dreamstime.com%2Fillustration%2Favatar-placeholder.html&psig=AOvVaw3aeE8x6-eP9VKhiDV8mBCP&ust=1603637821015000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCID518a-zewCFQAAAAAdAAAAABAD")
                    }
                    authorName(channel.title)
                }

                searchBoxView {
                    id("search")
                    onClick {
                        searchItem(it)
                    }
                }


                if(state.filteredPodCast.isNotEmpty()) {
                    state.filteredPodCast.forEach {
                        podCastView {
                            id(it.pubDate)
                            title(it.title)
                            pubDate(it.pubDate)
                            onShareClick {
                                shareEpisodeWithFriend(it.link,it.description,channel.title,it.title)
                            }
                        }
                    }
                } else {
                    channel.item.forEach {
                        podCastView {
                            id(it.pubDate)
                            title(it.title)
                            pubDate(it.pubDate)
                            onShareClick {
                                shareEpisodeWithFriend(it.link,it.description,channel.title,it.title)
                            }
                        }
                    }
                }
            }
            Uninitialized -> {}
            is Fail -> {}
        }
    }

    private fun shareEpisodeWithFriend(
        link: String,
        description: String,
        author: String,podcastTitle:String) {
        Log.d("link","$link -> $description")
        var  emailIntent =  Intent(Intent.ACTION_SEND);

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "A $author PodCast\n\n");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
           TextUtils.concat("I've found great $author podcast with $podcastTitle that you might like.\n\n" +
                    "Description: $description\n\n"+
                    "Link to podcast: $link").toString())
        emailIntent.type = "text/plain";
        startActivity(Intent.createChooser(emailIntent, "Send to friend"))
    }

    private fun searchItem(etSearch: TextInputEditText) {
        etSearch.addTextChangedListener(
            afterTextChanged = { homeVm.setSearchQuery(it.toString()) })
    }

    override fun invalidate() {
        withState(homeVm) {
            controller.requestModelBuild()
            homeVm.logStateChanges()
        }
    }

}