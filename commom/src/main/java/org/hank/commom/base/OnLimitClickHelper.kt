package org.hank.commom.base

import android.view.View
import org.hank.commom.listener.OnLimitClickListener
import java.util.*

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
 *创建时间: 2021/5/26 14:50
 *描述:
 */
class OnLimitClickHelper : View.OnClickListener{
    val LIMIT_TIME = 500
    private var lastClickTime: Long = 0
    private var onLimitClickListener: OnLimitClickListener? = null

    fun OnLimitClickHelper(onLimitClickListener: OnLimitClickListener?) {
        this.onLimitClickListener = onLimitClickListener
    }

    override fun onClick(v: View?) {
        val curTime = Calendar.getInstance().timeInMillis
        if (curTime - lastClickTime > LIMIT_TIME) {
            lastClickTime = curTime
            if (onLimitClickListener != null) {
                onLimitClickListener!!.onClick(v)
            }
        }
    }
}