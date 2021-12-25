package github.nocamles.demo

import retrofit2.http.*


interface ApiService {
    @GET("banner/json")
    suspend fun getHomeBanner(): HttpWrapBean<MutableList<HomeBannerBean>>
}