package com.example.mvvmarchitecture.viewmodel

import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmarchitecture.constant.EndpointConstants
import com.example.mvvmarchitecture.model.LoginUserDTO
import com.example.mvvmarchitecture.model.ProfileDTO
import com.example.mvvmarchitecture.model.WMSCoreAuthentication
import com.example.mvvmarchitecture.model.WMSCoreMessage
import com.example.mvvmarchitecture.repository.LoginRepository
import com.example.mvvmarchitecture.ui.LoginState
import com.google.gson.Gson
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository
) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {

            _loginState.value = LoginState.Loading

            val result = repository.login(buildLoginRequest(email, password))

            result.onSuccess { profile ->
                if(profile.type!!.equals("Exception")){
                    Log.d("Exception",profile.wmsMessages.toString());
                }else{
                    val user = Gson().fromJson(Gson().toJson(profile.entityObject), ProfileDTO::class.java)
                    if(user.IsSessionActive == "1"){
                        _loginState.value = LoginState.SessionActive(user)
                    }else{
                        _loginState.value = LoginState.Success(user)
                    }
                }
            }

            result.onFailure {
                _loginState.value = LoginState.Error(it.message ?: "Error")
            }
        }
    }

    private fun buildLoginRequest(email: String, password: String): WMSCoreMessage {
        val message = WMSCoreMessage()
        val token = WMSCoreAuthentication()

        token.authKey = "device_serial"
        token.userId = "1"
        token.loginTimeStamp = System.currentTimeMillis().toString()
        
        val loginDto = LoginUserDTO()
        loginDto.mailID = email
        loginDto.passwordEncrypted = password
        loginDto.isForceLogin = "0"

        message.type = EndpointConstants.LoginUserDTO
        message.authToken = token
        message.entityObject = loginDto

        return message
    }
}