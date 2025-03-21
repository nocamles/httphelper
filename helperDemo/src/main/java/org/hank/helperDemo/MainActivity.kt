package org.hank.helperDemo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import github.nocamles.httphelper.base.BaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.getValue

class MainActivity : BaseActivity() {
    override val layoutResId
        get() = R.layout.activity_main

    val viewModel by getViewModel(HttpViewModel::class.java){
        bannerBeanLiveData.observe(lLifecycleOwner){
            findViewById<TextView>(R.id.text_content).text = Gson().toJson(it)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<Button>(R.id.button_getbanner).setOnClickListener {
            findViewById<TextView>(R.id.text_content).text = ""
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