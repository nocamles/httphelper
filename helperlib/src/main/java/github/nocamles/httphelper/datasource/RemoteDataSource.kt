package github.nocamles.httphelper.datasource

import github.nocamles.httphelper.bean.IHttpWrapBean
import github.nocamles.httphelper.callback.RequestCallback
import github.nocamles.httphelper.exception.BaseHttpException
import github.nocamles.httphelper.exception.ServerCodeBadException
import github.nocamles.httphelper.viewmodel.IUIActionEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking

abstract class RemoteDataSource<Api : Any>(iUiActionEvent: IUIActionEvent?, apiServiceClass: Class<Api>) : BaseRemoteDataSource<Api>(iUiActionEvent, apiServiceClass) {

    fun <Data> enqueueLoading(apiFun: suspend Api.() -> IHttpWrapBean<Data>,
                              baseUrl: String = "",
                              callbackFun: (RequestCallback<Data>.() -> Unit)? = null): Job {
        return enqueue(apiFun = apiFun, showLoading = true, baseUrl = baseUrl, callbackFun = callbackFun)
    }

    fun <Data> enqueue(apiFun: suspend Api.() -> IHttpWrapBean<Data>,
                       showLoading: Boolean = false,
                       baseUrl: String = "",
                       callbackFun: (RequestCallback<Data>.() -> Unit)? = null): Job {
        return launchMain {
            val callback = if (callbackFun == null) null else RequestCallback<Data>().apply {
                callbackFun.invoke(this)
            }
            try {
                if (showLoading) {
                    showLoading(coroutineContext[Job])
                }
                callback?.onStart?.invoke()
                val response: IHttpWrapBean<Data>
                try {
                    response = apiFun.invoke(getApiService(baseUrl))
                    if (!response.httpIsSuccess) {
                        throw ServerCodeBadException(response)
                    }
                } catch (throwable: Throwable) {
                    handleException(throwable, callback)
                    return@launchMain
                }
                onGetResponse(callback, response.httpData)
            } finally {
                try {
                    callback?.onFinally?.invoke()
                } finally {
                    if (showLoading) {
                        dismissLoading()
                    }
                }
            }
        }
    }

    fun <Data> enqueueOriginLoading(apiFun: suspend Api.() -> Data,
                                    baseUrl: String = "",
                                    callbackFun: (RequestCallback<Data>.() -> Unit)? = null): Job {
        return enqueueOrigin(apiFun = apiFun, showLoading = true, baseUrl = baseUrl, callbackFun = callbackFun)
    }

    fun <Data> enqueueOrigin(apiFun: suspend Api.() -> Data,
                             showLoading: Boolean = false,
                             baseUrl: String = "",
                             callbackFun: (RequestCallback<Data>.() -> Unit)? = null): Job {
        return launchMain {
            val callback = if (callbackFun == null) null else RequestCallback<Data>().apply {
                callbackFun.invoke(this)
            }
            try {
                if (showLoading) {
                    showLoading(coroutineContext[Job])
                }
                callback?.onStart?.invoke()
                val response: Data
                try {
                    response = apiFun.invoke(getApiService(baseUrl))
                } catch (throwable: Throwable) {
                    handleException(throwable, callback)
                    return@launchMain
                }
                onGetResponse(callback, response)
            } finally {
                try {
                    callback?.onFinally?.invoke()
                } finally {
                    if (showLoading) {
                        dismissLoading()
                    }
                }
            }
        }
    }

    private suspend fun <Data> onGetResponse(callback: RequestCallback<Data>?, httpData: Data) {
        callback?.let {
            withNonCancellable {
                callback.onSuccess?.let {
                    withMain {
                        it.invoke(httpData)
                    }
                }
                callback.onSuccessIO?.let {
                    withIO {
                        it.invoke(httpData)
                    }
                }
            }
        }
    }

    /**
     * 同步请求，可能会抛出异常，外部需做好捕获异常的准备
     * @param apiFun
     */
    @Throws(BaseHttpException::class)
    fun <Data> execute(apiFun: suspend Api.() -> IHttpWrapBean<Data>, baseUrl: String = ""): Data {
        return runBlocking {
            try {
                val asyncIO = asyncIO {
                    apiFun.invoke(getApiService(baseUrl))
                }
                val response = asyncIO.await()
                if (response.httpIsSuccess) {
                    return@runBlocking response.httpData
                }
                throw ServerCodeBadException(response)
            } catch (throwable: Throwable) {
                throw generateBaseExceptionReal(throwable)
            }
        }
    }

}