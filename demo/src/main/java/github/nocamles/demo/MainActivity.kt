package github.nocamles.demo

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import github.nocamles.httphelper.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {
    override val layoutResId
        get() = R.layout.activity_main

    val viewModel by getViewModel(HttpViewModel::class.java){
        bannerBeanLiveData.observe(lLifecycleOwner){
            text_content.text = Gson().toJson(it)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button_getbanner.setOnClickListener {
            text_content.text = ""
            lifecycleScope.launch {
                delay(200)
                viewModel.getBanner()
            }
        }
    }

    override fun showToast(msg: String) {
        super.showToast(msg)
    }
}