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
    @POST("Item/AddItems")
    suspend fun AddItems(
        @Body request: WMSCoreMessage
    ): Response<String>

    @GET("Item/GetAllItems")
    suspend fun GetAllItems(
    ): Response<String>
}