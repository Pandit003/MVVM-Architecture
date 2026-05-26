package com.example.mvvmarchitecture.interfaces

import com.example.mvvmarchitecture.model.WMSCoreMessage
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @POST("Login/UserLogin")
    suspend fun userLogin(
        @Body request: WMSCoreMessage
    ): Response<String>
    @POST("Receive/AddItems")
    suspend fun AddItems(
        @Body request: WMSCoreMessage
    ): Response<String>
    @POST("Expense/AddExpense")
    suspend fun AddExpense(
        @Body request: WMSCoreMessage
    ): Response<String>

    @POST("Receive/GetAllItems")
    suspend fun GetAllItems(
        @Body request: WMSCoreMessage
    ): Response<String>
}