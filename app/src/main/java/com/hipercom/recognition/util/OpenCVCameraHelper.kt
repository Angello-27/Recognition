package com.hipercom.recognition.util

import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Mat
import com.hipercom.recognition.viewmodel.CameraViewModel

class OpenCVCameraHelper(private val viewModel: CameraViewModel) :
    CameraBridgeViewBase.CvCameraViewListener2 {

    override fun onCameraViewStarted(width: Int, height: Int) {

    }

    override fun onCameraViewStopped() {}

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Mat {
        val mat = inputFrame.rgba()
        viewModel.processCameraFrame(mat)
        return mat
    }
}
