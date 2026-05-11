package com.example.mvvmarchitecture.model

import com.google.gson.annotations.SerializedName

class WMSCoreAuthentication {
    @SerializedName("AuthKey")
    var authKey: String? = null

    @SerializedName("UserID")
    var userId: String? = null

    @SerializedName("AuthValue")
    var authValue: String? = null

    @SerializedName("LoginTimeStamp")
    var loginTimeStamp: String? = null

    @SerializedName("AuthToken")
    var authToken: String? = null

    @SerializedName("RequestNumber")
    var requestNumber: Int = 0

    @SerializedName("Locale")
    var locale: String? = null
}