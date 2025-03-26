package org.hank.helperDemo.bean
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import github.nocamles.httphelper.bean.IHttpWrapBean
import org.hank.helperDemo.base.HttpConfig

class HttpWrapBean<T>(
    @Expose @SerializedName("errorCode") var code: Int = 0,
    @Expose @SerializedName("errorMsg") var message: String? = null,
    @Expose @SerializedName("data", alternate = ["forecasts"]) var data: T
) : IHttpWrapBean<T> {

    companion object {

        fun <T> success(data: T): HttpWrapBean<T> {
            return HttpWrapBean(HttpConfig.CODE_SERVER_SUCCESS, "success", data)
        }

        fun <T> failed(data: T): HttpWrapBean<T> {
            return HttpWrapBean(-200, "服务器停止维护了~~", data)
        }

    }

    override val httpCode: Int
        get() = code

    override val httpMsg: String
        get() = message ?: ""

    override val httpData: T
        get() = data

    override val httpIsSuccess: Boolean
        get() = code == HttpConfig.CODE_SERVER_SUCCESS || message == "OK"

    override fun toString(): String {
        return "HttpResBean(code=$code, message=$message, data=$data)"
    }

}