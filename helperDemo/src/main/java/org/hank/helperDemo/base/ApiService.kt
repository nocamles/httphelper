package org.hank.helperDemo.base

import org.hank.helperDemo.bean.HomeBannerBean
import org.hank.helperDemo.bean.HttpWrapBean
import retrofit2.http.GET

interface ApiService {
    @GET("banner/json")
    suspend fun getHomeBanner(): HttpWrapBean<MutableList<HomeBannerBean>>
}