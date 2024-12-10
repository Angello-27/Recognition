package com.hipercom.recognition.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hipercom.recognition.repository.OpenCVRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.opencv.core.Mat

class CameraViewModel(private val openCVRepository: OpenCVRepository) : ViewModel() {

    fun processCameraFrame(inputFrame: Mat) {
        viewModelScope.launch(Dispatchers.IO) {
            val processedFrame = openCVRepository.processImage(inputFrame)
            // Aquí podrías enviar el frame procesado a la UI o hacer otro procesamiento
        }
    }
}
