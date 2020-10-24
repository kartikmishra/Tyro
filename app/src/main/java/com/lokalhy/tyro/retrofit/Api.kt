package com.lokalhy.tyro.retrofit


import com.lokalhy.tyro.model.RssModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface Api {

    @GET
    fun getData(@Url param:String) : Call<RssModel>

}