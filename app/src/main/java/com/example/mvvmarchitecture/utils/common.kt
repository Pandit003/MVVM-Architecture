package com.example.mvvmarchitecture.utils

import com.example.mvvmarchitecture.constant.EndpointConstants
import com.example.mvvmarchitecture.model.ItemDTO
import com.example.mvvmarchitecture.model.LoginUserDTO
import com.example.mvvmarchitecture.model.WMSCoreAuthentication
import com.example.mvvmarchitecture.model.WMSCoreMessage

class common {
    public fun buildLoginRequest(data: ItemDTO): WMSCoreMessage {
        val message = WMSCoreMessage()
        val token = WMSCoreAuthentication()

        token.authKey = "device_serial"
        token.userId = "1"
        token.loginTimeStamp = System.currentTimeMillis().toString()

        val loginDto = LoginUserDTO()


        message.type = EndpointConstants.LoginUserDTO
        message.authToken = token
        message.entityObject = loginDto

        return message
    }
}