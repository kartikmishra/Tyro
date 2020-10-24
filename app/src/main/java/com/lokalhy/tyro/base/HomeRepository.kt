package com.lokalhy.tyro.base

import com.lokalhy.tyro.DataSource

class HomeRepository(private val dataSource: DataSource) {

    fun fetchPodCasts(url:String,param:String) = dataSource.fetchPodCasts(url,param)
}