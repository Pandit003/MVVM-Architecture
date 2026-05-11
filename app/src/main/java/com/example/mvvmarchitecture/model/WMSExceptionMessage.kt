package com.example.mvvmarchitecture.model

import com.google.gson.annotations.SerializedName

class WMSExceptionMessage {
    @SerializedName("WMSMessage")
    var wMSMessage: String? = null

    @SerializedName("WMSExceptionCode ")
    var wMSExceptionCode: String? = null

    @SerializedName("ShowAsError")
    var isShowAsError: Boolean = false

    @SerializedName("ShowAsWarning")
    var isShowAsWarning: Boolean = false

    @SerializedName("ShowAsSuccess")
    var isShowAsSuccess: Boolean = false

    @SerializedName("ShowAsCriticalError")
    var isShowAsCriticalError: Boolean = false

    @SerializedName("ShowUserConfirmDialogue")
    var isShowUserConfirmDialogue: Boolean = false

    constructor()
    constructor(entries: MutableSet<out MutableMap.MutableEntry<*, *>>) {
        for (entry in entries) {
            when (entry.key.toString()) {
                "WMSMessage" -> if (entry.value != null) {
                    this.wMSMessage = entry.value.toString()
                }

                "ShowAsError" -> if (entry.value != null) {
                    this.isShowAsError = entry.value.toString().toBoolean()
                }

                "ShowAsWarning" -> if (entry.value != null) {
                    this.isShowAsWarning = entry.value.toString().toBoolean()
                }

                "ShowAsSuccess" -> if (entry.value != null) {
                    this.isShowAsSuccess = entry.value.toString().toBoolean()
                }

                "ShowAsCriticalError" -> if (entry.value != null) {
                    this.isShowAsCriticalError = entry.value.toString().toBoolean()
                }

                "ShowUserConfirmDialogue" -> if (entry.value != null) {
                    this.isShowUserConfirmDialogue = entry.value.toString().toBoolean()
                }

                "WMSExceptionCode" -> if (entry.value != null) {
                    this.wMSExceptionCode = entry.value.toString()
                }
            }
        }
    }
}
