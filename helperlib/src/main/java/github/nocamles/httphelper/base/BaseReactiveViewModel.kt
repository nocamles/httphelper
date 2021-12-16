package github.nocamles.httphelper.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.nocamles.httphelper.viewmodel.*
import kotlinx.coroutines.CoroutineScope

open class BaseReactiveViewModel : ViewModel(), IViewModelActionEvent {

    override val lifecycleSupportedScope: CoroutineScope
        get() = viewModelScope

    override val showLoadingEventLD = MutableLiveData<ShowLoadingEvent>()

    override val dismissLoadingEventLD = MutableLiveData<DismissLoadingEvent>()

    override val showToastEventLD = MutableLiveData<ShowToastEvent>()

    override val finishViewEventLD = MutableLiveData<FinishViewEvent>()

}

open class BaseReactiveAndroidViewModel(application: Application) : AndroidViewModel(application),
    IViewModelActionEvent {

    override val lifecycleSupportedScope: CoroutineScope
        get() = viewModelScope

    override val showLoadingEventLD = MutableLiveData<ShowLoadingEvent>()

    override val dismissLoadingEventLD = MutableLiveData<DismissLoadingEvent>()

    override val showToastEventLD = MutableLiveData<ShowToastEvent>()

    override val finishViewEventLD = MutableLiveData<FinishViewEvent>()

}