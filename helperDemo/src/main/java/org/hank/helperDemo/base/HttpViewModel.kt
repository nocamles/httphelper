package org.hank.helperDemo.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import org.hank.helperDemo.bean.HomeBannerBean

//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃                  神兽保佑
//    ┃　　　┃                  永无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛
/**
 *@auth: Hank
 *邮箱: cs16xiaoc1@163.com
 *创建时间: 2021/12/16 17:55
 *描述:
 */
class HttpViewModel : BaseViewModel() {
    val TAG = this.javaClass.name

    var bannerBeanLiveData: MutableLiveData<MutableList<HomeBannerBean>> = MutableLiveData()

    fun getBanner() {
        remoteDataSource.enqueueLoading({ getHomeBanner() }) {
            onSuccess {
                bannerBeanLiveData.postValue(it)
            }
            onFailed {
                Log.d(TAG, it.message!!)
            }
        }
    }
}