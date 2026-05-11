package com.example.mvvmarchitecture.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmarchitecture.model.ItemDTO
import com.example.mvvmarchitecture.model.ProfileDTO
import com.example.mvvmarchitecture.repository.ItemRepository
import com.example.mvvmarchitecture.services.RetrofitClient
import com.example.mvvmarchitecture.ui.NeedsState
import com.example.mvvmarchitecture.utils.common
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NeedsViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<ItemDTO>>(emptyList())
    val items = _items.asStateFlow()
    val repository = ItemRepository(RetrofitClient.apiService)
    private val _needsState = MutableLiveData<NeedsState>()
    val loginState: LiveData<NeedsState> = _needsState
    fun addItem(data: ItemDTO) {
        viewModelScope.launch {
            val result = repository.insertItems(common().buildLoginRequest(data))
            result.onSuccess { profile ->
                if(profile.type!!.equals("Exception")){
                    Log.d("Exception",profile.wmsMessages.toString());
                }else{
                    val user = Gson().fromJson(Gson().toJson(profile.entityObject), ProfileDTO::class.java)
                    if(user.IsSessionActive == "1"){
                        _needsState.value = NeedsState.ItemAdded(user)
                    }else{
                        _needsState.value = NeedsState.Success(user)
                    }
                }
            }

            result.onFailure {
                _needsState.value = NeedsState.Error(it.message ?: "Error")
            }
        }
    }
}