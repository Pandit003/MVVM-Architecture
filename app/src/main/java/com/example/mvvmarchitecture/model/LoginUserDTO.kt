package com.example.mvvmarchitecture.model

import com.google.gson.annotations.SerializedName

class LoginUserDTO {
    @SerializedName("MailID")
    var mailID: String? = null

    @SerializedName("PasswordEncrypted")
    var passwordEncrypted: String? = null

    @SerializedName("ClientMAC")
    var clientMAC: String? = null

    @SerializedName("SessionIdentifier")
    var sessionIdentifier: String? = null

    @SerializedName("CookieIdentifier")
    var cookieIdentifier: String? = null

    @SerializedName("IsForceLogin")
    var isForceLogin: String? = null
}
