package org.hank.commom.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.ConfirmPopupView
import com.lxj.xpopup.impl.LoadingPopupView
import github.leavesc.reactivehttp.viewmodel.IUIActionEventObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import org.hank.commom.R

abstract class BaseActivity : AppCompatActivity(), IUIActionEventObserver {
    abstract val layoutResId: Int

    override val lifecycleSupportedScope: CoroutineScope
        get() = lifecycleScope

    override val lContext: Context?
        get() = this

    override val lLifecycleOwner: LifecycleOwner
        get() = this

    lateinit var rootView: View

    private var LoadingPopupView: LoadingPopupView? = null
    lateinit var xPop: ConfirmPopupView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        //暗色  statusbar透明
        immersive(true)
        rootView = (findViewById<ViewGroup>(android.R.id.content))
    }

    protected fun <T : Activity> startActivity(clazz: Class<T>) {
        lContext?.apply {
            startActivity(Intent(this, clazz))
        }
    }

    override fun showLoading(job: Job?) {
        dismissLoading()
        LoadingPopupView = XPopup.Builder(this)
            .dismissOnTouchOutside(false)
            .asLoading()
        LoadingPopupView!!.dismissWith {
            //弹框被关闭 关闭请求
            job!!.cancel()
        }
        LoadingPopupView!!.show()
    }

    override fun dismissLoading() {
        LoadingPopupView?.takeIf { it.isShow }?.dismiss()
        LoadingPopupView = null
    }

    override fun showToast(msg: String) {
        if (msg.isNotBlank()) {
            xPop = XPopup.Builder(this)
                .asConfirm(resources.getString(R.string.tips), msg) { xPop.dismiss() }
            xPop.show()
        }
    }

    override fun finishView() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
    }
}