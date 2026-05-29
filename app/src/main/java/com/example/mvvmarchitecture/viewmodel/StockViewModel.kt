package com.example.mvvmarchitecture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmarchitecture.constant.EndpointConstants
import com.example.mvvmarchitecture.model.GetDataDTO
import com.example.mvvmarchitecture.model.ReceivedItemDTO
import com.example.mvvmarchitecture.model.WMSExceptionMessage
import com.example.mvvmarchitecture.repository.StockRepository
import com.example.mvvmarchitecture.services.RetrofitClient
import com.example.mvvmarchitecture.ui.NeedsState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StockViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<ReceivedItemDTO>>(emptyList())
    val items = _items.asStateFlow()

    private val repository = StockRepository(RetrofitClient.apiService)

    private val _stockState = MutableLiveData<NeedsState>()
    val stockState: LiveData<NeedsState> = _stockState

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getStockItems() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getAllItems(GetDataDTO(isGetNames = "1"))
                result.onSuccess { item ->
                    if (item.type == EndpointConstants.Exception) {
                        val type = object : TypeToken<List<WMSExceptionMessage>>() {}.type
                        val exList: List<WMSExceptionMessage> = Gson().fromJson(
                            Gson().toJson(item.entityObject),
                            type
                        )
                        _stockState.value = NeedsState.Exception(exList[0].wMSMessage.toString())
                    } else {
                        val type = object : TypeToken<List<ReceivedItemDTO>>() {}.type
                        val itemList: List<ReceivedItemDTO> = Gson().fromJson(
                            Gson().toJson(item.entityObject),
                            type
                        )
                        _items.value = itemList
                    }
                }
                result.onFailure {
                    _stockState.value = NeedsState.Failure(it.message ?: "Failed to get stock")
                }
            } catch (e: Exception) {
                _stockState.value = NeedsState.Failure(e.message ?: "An error occurred")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
