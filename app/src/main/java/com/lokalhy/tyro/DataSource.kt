package com.lokalhy.tyro

import android.util.Log
import com.lokalhy.tyro.model.Channel
import com.lokalhy.tyro.model.RssModel
import com.lokalhy.tyro.retrofit.PodCastApiClient
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response

class DataSource {



    fun fetchPodCasts(url:String,param:String): Observable<RssModel> {
        Log.d("url-> param","$url$param")
        return Observable.create<RssModel> {
            PodCastApiClient.getmInstance().api.getData("$url$param")
                    .enqueue(object :retrofit2.Callback<RssModel>{
                override fun onFailure(call: Call<RssModel>, t: Throwable) {
                    Log.d("on Failure","${t.message}")
                    it.onError(t)
                }

                override fun onResponse(call: Call<RssModel>, response: Response<RssModel>) {
                    if(response.isSuccessful) {
                        val rss = response.body()!!
                        it.onNext(rss)
                    }
                }
            })

        }
    }

}