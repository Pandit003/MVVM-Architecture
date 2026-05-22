package com.example.mvvmarchitecture.repository

import android.util.Log
import com.example.mvvmarchitecture.interfaces.ApiService
import com.example.mvvmarchitecture.model.ItemDTO
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
                Result.success(parsedResponse)

            } else {
                Result.failure(Exception("Failed to add item"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun getAllItems(): Result<WMSCoreMessage> {
        return try {
            val response = apiService.GetAllItems()

            Log.d("TAG", "Raw response: ${response.body()}")

            if (response.isSuccessful && response.body() != null) {

                val jsonString = response.body()!!

                val parsedResponse = Gson().fromJson(
                    jsonString,
                    WMSCoreMessage::class.java
                )
                Result.success(parsedResponse)

            } else {
                Result.failure(Exception("Failed to get all items"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}