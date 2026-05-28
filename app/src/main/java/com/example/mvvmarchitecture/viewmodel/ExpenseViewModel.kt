package com.example.mvvmarchitecture.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmarchitecture.constant.EndpointConstants
import com.example.mvvmarchitecture.model.ExpenseItemDTO
import com.example.mvvmarchitecture.model.GetDataDTO
import com.example.mvvmarchitecture.model.ReceivedItemDTO
import com.example.mvvmarchitecture.model.ProfileDTO
import com.example.mvvmarchitecture.model.WMSExceptionMessage
import com.example.mvvmarchitecture.repository.ExpenseRepository
import com.example.mvvmarchitecture.services.RetrofitClient
import com.example.mvvmarchitecture.ui.NeedsState
import com.example.mvvmarchitecture.utils.common
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExpenseViewModel : ViewModel() {

    private val _availableItems = MutableStateFlow<List<ExpenseItemDTO>>(emptyList())
    val availableItems = _availableItems.asStateFlow()

    private val _expenses = MutableStateFlow<List<ExpenseItemDTO>>(emptyList())
    val expenses = _expenses.asStateFlow()

    private val repository = ExpenseRepository(RetrofitClient.apiService)

    private val _expenseState = MutableLiveData<NeedsState>()
    val expenseState: LiveData<NeedsState> = _expenseState

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _saveSuccess = MutableLiveData<Boolean>()
    val saveSuccess: LiveData<Boolean> = _saveSuccess

    fun getNamesAndUnit() {
        viewModelScope.launch {
            try {
                val result = repository.getNames(GetDataDTO(isGetNames = "1"))
                result.onSuccess { item ->
                    if (item.type == EndpointConstants.Exception) {
                        val type = object : TypeToken<List<WMSExceptionMessage>>() {}.type
                        val exList: List<WMSExceptionMessage> = Gson().fromJson(
                            Gson().toJson(item.entityObject),
                            type
                        )
                        _expenseState.value = NeedsState.Exception(exList[0].wMSMessage.toString())
                    } else {
                        val type = object : TypeToken<List<ExpenseItemDTO>>() {}.type
                        val itemList: List<ExpenseItemDTO> = Gson().fromJson(
                            Gson().toJson(item.entityObject),
                            type
                        )
                        _availableItems.value = itemList
                        // For the list in ExpenseFragment, we show the same items or filtered ones
//                        _expenses.value = itemList
                    }
                }
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Error fetching items", e)
                _expenseState.value = NeedsState.Failure(e.message ?: "An error occurred")
            }
        }
    }
    fun getAllExpenseItems() {
        viewModelScope.launch {
            try {
                val result = repository.getAllItems(GetDataDTO(isGetExpenses = "1"))
                result.onSuccess { item ->
                    if (item.type == EndpointConstants.Exception) {
                        val type = object : TypeToken<List<WMSExceptionMessage>>() {}.type
                        val exList: List<WMSExceptionMessage> = Gson().fromJson(
                            Gson().toJson(item.entityObject),
                            type
                        )
                        _expenseState.value = NeedsState.Exception(exList[0].wMSMessage.toString())
                    } else {
                        val type = object : TypeToken<List<ExpenseItemDTO>>() {}.type
                        val itemList: List<ExpenseItemDTO> = Gson().fromJson(
                            Gson().toJson(item.entityObject),
                            type
                        )
                        _expenses.value = itemList
                    }
                }
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Error fetching items", e)
                _expenseState.value = NeedsState.Failure(e.message ?: "An error occurred")
            }
        }
    }

    fun addExpense(data: ExpenseItemDTO) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.AddExpenses(common().buildExpenseRequest(data))
                result.onSuccess { item ->
                    if (item.type == EndpointConstants.Exception) {
                        val type = object : TypeToken<List<WMSExceptionMessage>>() {}.type
                        val exList: List<WMSExceptionMessage> = Gson().fromJson(
                            Gson().toJson(item.entityObject),
                            type
                        )
                        _expenseState.value = NeedsState.Exception(exList[0].wMSMessage.toString())
                    } else {
                        val type = object : TypeToken<List<ExpenseItemDTO>>() {}.type
                        val itemList: List<ExpenseItemDTO> = Gson().fromJson(
                            Gson().toJson(item.entityObject),
                            type
                        )
                        _expenses.value = itemList
                        _saveSuccess.value = true
                        _expenseState.value = NeedsState.Success(ProfileDTO())
                    }
                }
                result.onFailure {
                    _expenseState.value = NeedsState.Failure(it.message ?: "Failed to add expense")
                }
            } catch (e: Exception) {
                _expenseState.value = NeedsState.Failure(e.message ?: "Something went wrong")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
