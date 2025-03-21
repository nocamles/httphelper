package org.hank.helperDemo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class HomeBannerBean(
    @Expose @SerializedName("desc")
    var desc: String = "",
    @Expose @SerializedName("id")
    var id: Int = 0,
    @Expose @SerializedName("imagePath")
    var imagePath: String = "",
    @Expose @SerializedName("isVisible")
    var isVisible: Int = 0,
    @Expose @SerializedName("order")
    var order: Int = 0,
    @Expose @SerializedName("title")
    var title: String = "",
    @Expose @SerializedName("type")
    var type: Int = 0,
    @Expose @SerializedName("url")
    var url: String = "",
    @Expose @SerializedName("testAdd")
    var testAdd:String = "",
)