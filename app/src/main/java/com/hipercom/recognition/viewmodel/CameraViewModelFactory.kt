package com.hipercom.recognition.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hipercom.recognition.repository.OpenCVRepository

class CameraViewModelFactory(
    private val openCVRepository: OpenCVRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CameraViewModel::class.java)) {
            return CameraViewModel(openCVRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
