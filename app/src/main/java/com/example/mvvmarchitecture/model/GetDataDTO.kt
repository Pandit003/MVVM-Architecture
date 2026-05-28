package com.example.mvvmarchitecture.model

import com.google.gson.annotations.SerializedName

data class GetDataDTO(

    @SerializedName("isUpdate")
    var isUpdate: String? = "0",

    @SerializedName("isGetAllData")
    var isGetAllData: String? = "0",

    @SerializedName("isGetNames")
    var isGetNames: String? = "0",
    @SerializedName("isGetExpenses")
    var isGetExpenses: String? = "0",
)