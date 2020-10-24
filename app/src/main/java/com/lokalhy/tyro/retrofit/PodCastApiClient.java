package com.lokalhy.tyro.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class PodCastApiClient {

    private String BASE_URL = "http://joeroganexp.joerogan.libsynpro.com/";

    private static PodCastApiClient mInstance;
    private Retrofit retrofit;


    private PodCastApiClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
    }


    public static synchronized PodCastApiClient getmInstance(){
        if(mInstance==null){
            mInstance = new PodCastApiClient();
        }
        return mInstance;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
