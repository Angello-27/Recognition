package com.hipercom.recognition.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CameraViewModel : ViewModel() {
    private val _cameraAction = Channel<CameraAction>()
    val cameraAction = _cameraAction.receiveAsFlow()

    fun onCameraButtonClick() {
        viewModelScope.launch {
            _cameraAction.send(CameraAction.Capture)
        }
    }

    sealed class CameraAction {
        object Capture : CameraAction()
    }
}
