package com.hipercom.recognition.repository

import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class OpenCVRepository {

    fun processImage(inputMat: Mat): Mat {
        // Aquí aplicarás cualquier procesamiento de imagen, por ejemplo, convertir a gris
        val outputMat = Mat()
        Imgproc.cvtColor(inputMat, outputMat, Imgproc.COLOR_RGBA2GRAY)
        return outputMat
    }
}
