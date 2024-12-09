package com.hipercom.recognition.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hipercom.recognition.repository.PermissionRepository

class MainViewModelFactory(
    private val permissionRepository: PermissionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(permissionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}