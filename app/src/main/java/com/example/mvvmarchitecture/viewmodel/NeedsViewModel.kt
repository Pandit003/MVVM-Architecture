package com.example.mvvmarchitecture.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmarchitecture.constant.EndpointConstants
import com.example.mvvmarchitecture.model.GetDataDTO
import com.example.mvvmarchitecture.model.ReceivedItemDTO
import com.example.mvvmarchitecture.model.WMSExceptionMessage
import com.example.mvvmarchitecture.repository.ReceiveRepository
import com.example.mvvmarchitecture.services.RetrofitClient
import com.example.mvvmarchitecture.ui.NeedsState
import com.example.mvvmarchitecture.utils.common
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NeedsViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<ReceivedItemDTO>>(emptyList())
    // Keep internal list for filtering
    val items = _items.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // Filtered items based on search query
    val filteredItems: StateFlow<List<ReceivedItemDTO>> = combine(_items, _searchQuery) { items, query ->
        if (query.isBlank()) {
            items
        } else {
            items.filter { item ->
                item.ItemName?.contains(query, ignoreCase = true) == true ||
                item.category?.contains(query, ignoreCase = true) == true
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val repository = ReceiveRepository(RetrofitClient.apiService)
    private val _needsState = MutableLiveData<NeedsState>()
    val needState: LiveData<NeedsState> = _needsState
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _saveSuccess = MutableLiveData<Boolean>()
    val saveSuccess: LiveData<Boolean> = _saveSuccess

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun getAllItems() {
        viewModelScope.launch {
            try {
                val result = repository.getAllItems(GetDataDTO(isGetAllData = "1"))
                result.onSuccess { item ->
                    if (item.type == EndpointConstants.Exception) {
                        val type = object : TypeToken<List<WMSExceptionMessage>>() {}.type
                        val exList: List<WMSExceptionMessage> = Gson().fromJson(
                            Gson().toJson(item.entityObject),
                            type
                        )
                        _needsState.value = NeedsState.Exception(exList[0].wMSMessage.toString() ?: "Exception occurred")
                        Log.d("Exception", exList[0].wMSMessage.toString())
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
                    _needsState.value = NeedsState.Failure(it.message ?: "Failed to get all items")
                }
            }catch (e: Exception) {
                _needsState.value = NeedsState.Failure(e.message ?: "An error occurred")
            }
        }
    }
    fun addItem(data: ReceivedItemDTO) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.insertItems(common().buildReceiveRequest(data))
                result.onSuccess { item ->
                    if (item.type!!.equals("Exception")) {
                        _needsState.value = NeedsState.Failure(item.wmsMessages.toString())
                    } else {
                        val type = object : TypeToken<List<ReceivedItemDTO>>() {}.type
                        val itemList: List<ReceivedItemDTO> = Gson().fromJson(
                            Gson().toJson(item.entityObject),
                            type
                        )
                        _items.value = itemList
                        _saveSuccess.value = true
                    }
                }
                result.onFailure {
                    _needsState.value = NeedsState.Failure(it.message ?: "Failed to add item")
                }
            } catch (e: Exception) {
                _needsState.value = NeedsState.Failure(e.message ?: "Something went wrong")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
