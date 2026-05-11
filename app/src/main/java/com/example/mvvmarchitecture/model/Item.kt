package com.example.mvvmarchitecture.model

import com.google.gson.annotations.SerializedName

class ItemDTO(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("itemId")
    val itemId: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("quantity")
    val quantity: Double? = null,

    @SerializedName("cost")
    val cost: Double? = null,

    @SerializedName("unit")
    val unit: String? = null,

    @SerializedName("category")
    val category: String? = null,

    @SerializedName("priority")
    val priority: String? = null,

    @SerializedName("receiveQty")
    val receiveQty: Double? = null,

    @SerializedName("avlQty")
    val avlQty: Double? = null,

    @SerializedName("time")
    val time: String? = null,

    @SerializedName("totalCost")
    val totalCost: String? = null,

    @SerializedName("individualCost")
    val individualCost: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("emptyItems")
    val emptyItems: List<Int>? = null
)