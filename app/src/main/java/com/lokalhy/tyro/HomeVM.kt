package com.lokalhy.tyro

import android.util.Log
import com.airbnb.mvrx.*
import com.lokalhy.tyro.base.HomeRepository
import com.lokalhy.tyro.base.MvrxViewModel
import com.lokalhy.tyro.base.UserApplication
import com.lokalhy.tyro.model.Channel
import com.lokalhy.tyro.model.Item
import com.lokalhy.tyro.model.RssModel


class HomeVM(initialState:HomeState,
             private val homeRepository: HomeRepository
) : MvrxViewModel<HomeState>(initialState) {

    init {
        getPodCasts()
    }

    fun setViewPagerPageNumber(pageNumber: Int) {
        setState { copy(page = pageNumber) }
    }

    fun getPodCasts() = withState {
        if(it.url != "" && it.param != "") {
            homeRepository.fetchPodCasts(it.url,it.param)
                    .execute {
                        if(it is Success) {
                            Log.d("sucess -> ", "")
                        }
                        copy(channel= it)
                    }
        }
    }

    fun setBaseUrl(url: String) {
        Log.d("url","$url")
        setState { copy(url = url) }
    }

    fun setParam(param: String) {
        Log.d("param","$param")
        setState { copy(param = param) }
    }

    fun setSearchQuery(query: String) {
        setState { copy(searchQuery = query) }
    }

    fun setViewPager(viewPager: NoSwipeViewPager) {
        setState { copy(viewPager = viewPager) }
    }

    fun setItemList(list: List<Item>) {
        setState { copy(item = list) }
    }

    companion object : MvRxViewModelFactory<HomeVM, HomeState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: HomeState
        ): HomeVM? {
            val homeRepository = viewModelContext.app<UserApplication>().homeRepository
            return HomeVM(state,homeRepository)
        }
    }
}

data class HomeState(val channel:Async<RssModel> = Uninitialized, var page:Int=0,
                     val viewPager: NoSwipeViewPager? =null,
                     val searchQuery:String="",
                     val url:String="",
                     val param:String="",
                      val item:List<Item> = listOf()) : MvRxState {

    val filteredPodCast = item.filter {
        it.title.contains(searchQuery,ignoreCase = true)
    }
}
