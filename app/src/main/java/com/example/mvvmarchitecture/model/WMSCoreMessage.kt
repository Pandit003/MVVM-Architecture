package com.example.mvvmarchitecture.model

import com.example.mvvmarchitecture.constant.EndpointConstants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WMSCoreMessage {

    @SerializedName("AuthToken")
    @Expose
    var authToken: WMSCoreAuthentication? = null

    @SerializedName("EntityObject")
    @Expose
    var entityObject: Any? = null

    @SerializedName("Type")
    @Expose
    var type: EndpointConstants? = null

    @SerializedName("WMSMessages")
    @Expose
    var wmsMessages: MutableList<WMSExceptionMessage?>? = null
}
