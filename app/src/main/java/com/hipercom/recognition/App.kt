package com.hipercom.recognition

import android.app.Application
import android.util.Log
import org.opencv.android.OpenCVLoader

class App : Application() {
    companion object {
        private const val TAG = "App"
    }

    override fun onCreate() {
        super.onCreate()
        initializeOpenCV()
    }

    private fun initializeOpenCV() {
        if (OpenCVLoader.initDebug()) {
            Log.i(TAG, "OpenCV loaded successfully")
        } else {
            Log.e(TAG, "Failed to load OpenCV")
        }
    }
}
