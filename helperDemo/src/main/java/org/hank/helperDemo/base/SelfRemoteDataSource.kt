package org.hank.helperDemo.base

import com.franmontiel.persistentcookiejar.BuildConfig
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor

import github.nocamles.httphelper.datasource.RemoteExtendDataSource
import github.nocamles.httphelper.viewmodel.IUIActionEvent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.hank.helperDemo.base.ApiService
import org.hank.helperDemo.base.HttpConfig
import org.hank.helperDemo.MyApplication
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class SelfRemoteDataSource(iActionEvent: IUIActionEvent?) :
    RemoteExtendDataSource<ApiService>(iActionEvent, ApiService::class.java) {
    companion object {

        private val httpClient: OkHttpClient by lazy {
            createHttpClient()
        }

        private fun createHttpClient(): OkHttpClient {
            var interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            var cookieJar =
                PersistentCookieJar(
                    SetCookieCache(),
                    SharedPrefsCookiePersistor(MyApplication.Companion.context)
                )

            val builder = OkHttpClient.Builder()
                .readTimeout(5000L, TimeUnit.MILLISECONDS)
                .writeTimeout(5000L, TimeUnit.MILLISECONDS)
                .connectTimeout(5000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
            if (BuildConfig.DEBUG) builder.addInterceptor(interceptor)
            return builder.build()
        }

    }

    /**
     * 由子类实现此字段以便获取 baseUrl
     */
    override val baseUrl: String
        get() = HttpConfig.BASE_URL_MAP

    /**
     * 允许子类自己来实现创建 Retrofit 的逻辑
     * 外部无需缓存 Retrofit 实例，ReactiveHttp 内部已做好缓存处理
     * 但外部需要自己判断是否需要对 OKHttpClient 进行缓存
     * @param baseUrl
     */
    override fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}