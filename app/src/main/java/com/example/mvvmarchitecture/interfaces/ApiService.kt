package com.example.mvvmarchitecture.interfaces

import com.example.mvvmarchitecture.model.WMSCoreMessage
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface ApiService {

    @POST("Login/UserLogin")
    suspend fun userLogin(
        @Body request: WMSCoreMessage
    ): Response<String>
    @POST("Item/AddItems")
    suspend fun AddItems(
        @Body request: WMSCoreMessage
    ): Response<String>
}