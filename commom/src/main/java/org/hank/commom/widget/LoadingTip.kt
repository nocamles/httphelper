package org.hank.commom.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.wang.avi.AVLoadingIndicatorView
import org.hank.commom.R
import org.hank.commom.listener.ReloadListener

/**
 * des 辅助站位图
 *
 * @author zs
 * @date 2020-03-12
 */
class LoadingTip : RelativeLayout {

    private var llEmpty: LinearLayout? = null
    private var indicatorView: AVLoadingIndicatorView? = null
    private var llInternetError: LinearLayout? = null

    private var reloadListener: ReloadListener? = null

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context) {
        var view = inflate(context, R.layout.loading_tip, this)
        llEmpty = view.findViewById(R.id.llEmpty)
        indicatorView = view.findViewById(R.id.indicatorView)
        llInternetError = view.findViewById(R.id.llInternetError)
        llInternetError?.setOnClickListener {
            reloadListener?.reload()
        }
    }

    fun setReloadListener(reloadListener:ReloadListener){
        this.reloadListener = reloadListener
    }

    /**
     * 显示空白页
     */
    fun showEmpty() {
        visibility = VISIBLE
        llEmpty?.visibility = VISIBLE
        indicatorView?.visibility = GONE
        indicatorView?.hide()

        llInternetError?.visibility = GONE
    }

    /**
     * 显示网络错误
     */
    fun showInternetError() {
        visibility = VISIBLE
        llInternetError?.visibility = VISIBLE
        llEmpty?.visibility = GONE
        indicatorView?.visibility = GONE
        indicatorView?.hide()
    }

    /**
     * 加载
     */
    fun loading() {
        visibility = VISIBLE
        indicatorView?.visibility = VISIBLE
        indicatorView?.show()
        llInternetError?.visibility = GONE
        llEmpty?.visibility = GONE

    }

    /**
     * 隐藏loadingTip
     */
    fun dismiss() {
        indicatorView?.hide()
        visibility = GONE
    }
}