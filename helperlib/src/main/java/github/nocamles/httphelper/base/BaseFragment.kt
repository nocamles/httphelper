package github.nocamles.httphelper.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.ConfirmPopupView
import com.lxj.xpopup.impl.LoadingPopupView
import github.nocamles.httphelper.viewmodel.IUIActionEventObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

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
 *创建时间: 2021/4/15 17:10
 *描述:
 */
abstract class BaseFragment : Fragment,
    IUIActionEventObserver {
    var layoutRes: Int = -1

    constructor() : super()

    constructor(layoutRes: Int) : super(layoutRes) {
        this.layoutRes = layoutRes
    }


    override val lifecycleSupportedScope: CoroutineScope
        get() = lifecycleScope

    override val lContext: Context?
        get() = context

    override val lLifecycleOwner: LifecycleOwner
        get() = this

    private var LoadingPopupView: LoadingPopupView? = null
    lateinit var xPop: ConfirmPopupView
    lateinit var rootView: View

    override fun showLoading(job: Job?) {
        dismissLoading()
        LoadingPopupView = XPopup.Builder(context)
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
            xPop = XPopup.Builder(context)
                .asConfirm("提示", msg) { xPop.dismiss() }
            xPop.show()
        }
    }

    override fun finishView() {
        activity!!.onBackPressed()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = super.onCreateView(inflater, container, savedInstanceState)!!
        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
    }
}