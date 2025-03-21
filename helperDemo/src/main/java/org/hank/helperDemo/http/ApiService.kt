package org.hank.helperDemo.http

import retrofit2.http.GET

interface ApiService {
    @GET("banner/json")
    suspend fun getHomeBanner(): HttpWrapBean<MutableList<HomeBannerBean>>
}