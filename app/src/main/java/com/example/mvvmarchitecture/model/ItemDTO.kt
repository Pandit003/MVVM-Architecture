package com.example.mvvmarchitecture.model

import com.google.gson.annotations.SerializedName

class ItemDTO(

    @SerializedName("Id")
    var id: Int? = null,

    @SerializedName("ItemId")
    var itemId: Int? = null,

    @SerializedName("Name")
    var name: String? = null,

    @SerializedName("Quantity")
    var quantity: Double? = null,

    @SerializedName("Cost")
    var cost: Double? = null,

    @SerializedName("Unit")
    var unit: String? = null,

    @SerializedName("Category")
    var category: String? = null,

    @SerializedName("Priority")
    var priority: String? = null,

    @SerializedName("ReceiveQty")
    var receiveQty: Double? = null,

    @SerializedName("AvailableQty")
    var availableQty: Double? = null,

    @SerializedName("RequiredQty")
    var requiredQty: Double? = null,

    @SerializedName("Time")
    var time: String? = null,

    @SerializedName("TotalCost")
    var totalCost: String? = null,

    @SerializedName("IndividualCost")
    var individualCost: String? = null,

    @SerializedName("Description")
    var description: String? = null,

    @SerializedName("isUpdate")
    var isUpdate: String? = null,

    @SerializedName("EmptyItems")
    var emptyItems: List<Int>? = null
)