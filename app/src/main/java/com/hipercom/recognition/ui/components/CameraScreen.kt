package com.hipercom.recognition.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hipercom.recognition.viewmodel.CameraViewModel
import androidx.activity.result.ActivityResultLauncher

@Composable
fun CameraScreen(
    viewModel: CameraViewModel,
    launcher: ActivityResultLauncher<Void?>
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { viewModel.onCameraButtonClick() }) {
            Text("Abrir Cámara")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.cameraAction.collect { action ->
            when (action) {
                CameraViewModel.CameraAction.Capture -> {
                    launcher.launch(null)  // No parameters are needed for this contract
                }
            }
        }
    }
}
