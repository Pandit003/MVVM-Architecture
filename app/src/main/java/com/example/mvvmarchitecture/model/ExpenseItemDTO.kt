package com.example.mvvmarchitecture.model

import com.google.gson.annotations.SerializedName

data class ExpenseItemDTO(

    @SerializedName("ExpenseId")
    var expenseId: Int? = 0,

    @SerializedName("ItemId")
    var itemId: Int? = null,

    @SerializedName("ItemName")
    var ItemName: String? = null,

    @SerializedName("Unit")
    var unit: String? = null,

    @SerializedName("ExpenseQty")
    val expenseQty: Double? = null,

    @SerializedName("IssuedTo")
    val issuedTo: String? = null,

    @SerializedName("Remarks")
    val remarks: String? = null,

    @SerializedName("ExpenseTime")
    var expenseTime: String? = null,

    @SerializedName("CreatedBy")
    var createdBy: Int? = null,

    @SerializedName("Description")
    var description: String? = null,

    @SerializedName("Category")
    var category: String? = null,

    @SerializedName("AvailableQty")
    var availableQty: Double? = null,

    @SerializedName("isUpdate")
    var isUpdate: String? = null,
)