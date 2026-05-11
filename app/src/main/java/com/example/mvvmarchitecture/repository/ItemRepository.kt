package com.example.mvvmarchitecture.repository

import android.util.Log
import com.example.mvvmarchitecture.interfaces.ApiService
import com.example.mvvmarchitecture.model.LoginUserDTO
import com.example.mvvmarchitecture.model.WMSCoreMessage
import com.google.gson.Gson


class ItemRepository(private val apiService: ApiService) {

    suspend fun insertItems(request: WMSCoreMessage): Result<WMSCoreMessage> {
        return try {
            val response = apiService.AddItems(request)

            Log.d("TAG", "Raw response: ${response.body()}")

            if (response.isSuccessful && response.body() != null) {

                val jsonString = response.body()!!

                val parsedResponse = Gson().fromJson(
                    jsonString,
                    WMSCoreMessage::class.java
                )
                val LoginUserDTO = Gson().fromJson(
                    Gson().toJson(parsedResponse.entityObject),
                    LoginUserDTO::class.java
                )
                Log.d("Response",LoginUserDTO.mailID.toString())
                Result.success(parsedResponse)

            } else {
                Result.failure(Exception("Login failed"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}