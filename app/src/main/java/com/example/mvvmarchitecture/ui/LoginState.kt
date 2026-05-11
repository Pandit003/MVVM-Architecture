package com.example.mvvmarchitecture.ui

import com.example.mvvmarchitecture.model.ProfileDTO
import com.example.mvvmarchitecture.model.WMSCoreMessage

sealed class LoginState {

    object Idle : LoginState()

    object Loading : LoginState()

    data class Success(
        val profile: ProfileDTO
    ) : LoginState()

    data class SessionActive(
        val profile: ProfileDTO
    ) : LoginState()

    data class Error(
        val message: String
    ) : LoginState()
}