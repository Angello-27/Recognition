package com.hipercom.recognition.ui.components

import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.hipercom.recognition.util.OpenCVCameraHelper
import com.hipercom.recognition.viewmodel.CameraViewModel
import org.opencv.android.JavaCameraView

@Preview
@Composable
fun CameraScreen(javaCameraView: JavaCameraView) {
    AndroidView(
        factory = { javaCameraView },
        modifier = Modifier.fillMaxSize(),
        update = { _ ->
            // Update the camera view if necessary
        }
    )
}
