package com.example.mvvmarchitecture.repository

import android.util.Log
import com.example.mvvmarchitecture.constant.EndpointConstants
import com.example.mvvmarchitecture.interfaces.ApiService
import com.example.mvvmarchitecture.model.GetDataDTO
import com.example.mvvmarchitecture.model.WMSCoreAuthentication
import com.example.mvvmarchitecture.model.WMSCoreMessage
import com.google.gson.Gson


class ExpenseRepository(private val apiService: ApiService) {

    suspend fun AddExpenses(request: WMSCoreMessage): Result<WMSCoreMessage> {
        return try {
            Log.d("Request", "${Gson().toJson(request)}")
            val response = apiService.AddExpense(request)

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

    suspend fun getNames(getData: GetDataDTO): Result<WMSCoreMessage> {
        return try {
            val message = WMSCoreMessage()
            val token = WMSCoreAuthentication()

            token.authKey = "device_serial"
            token.userId = "1"
            token.loginTimeStamp = System.currentTimeMillis().toString()

            message.type = EndpointConstants.GetDataDTO
            message.authToken = token
            message.entityObject = getData

            val response = apiService.GetAllItems(message)

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
    suspend fun getAllItems(getData: GetDataDTO): Result<WMSCoreMessage> {
        return try {
            val message = WMSCoreMessage()
            val token = WMSCoreAuthentication()

            token.authKey = "device_serial"
            token.userId = "1"
            token.loginTimeStamp = System.currentTimeMillis().toString()

            message.type = EndpointConstants.GetDataDTO
            message.authToken = token
            message.entityObject = getData

            val response = apiService.GetAllExpenses(message)

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