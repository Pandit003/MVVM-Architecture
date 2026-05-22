package com.example.mvvmarchitecture.ui

import com.example.mvvmarchitecture.model.ProfileDTO

sealed class NeedsState {

    object Idle : NeedsState()

    object Loading : NeedsState()

    data class Success(
        val profile: ProfileDTO
    ) : NeedsState()

    data class ItemAdded(
        val profile: ProfileDTO
    ) : NeedsState()


    data class Error(
        val message: String
    ) : NeedsState()

    data class Failure(
        val message: String
    ) : NeedsState()

    data class Exception(
        val message: String
    ) : NeedsState()
}