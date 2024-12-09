package com.hipercom.recognition.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hipercom.recognition.model.PermissionState
import com.hipercom.recognition.repository.PermissionRepository

class MainViewModel : ViewModel() {
    private val permissionRepository = PermissionRepository()
    val permissionsState = MutableLiveData<PermissionState>()

    fun checkPermissions(context: Context) {
        permissionRepository.checkPermissions(context, permissionsState)
    }
}