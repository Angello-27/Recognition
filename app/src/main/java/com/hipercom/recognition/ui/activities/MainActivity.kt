package com.hipercom.recognition.ui.activities

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hipercom.recognition.R
import com.hipercom.recognition.model.PermissionState
import com.hipercom.recognition.repository.OpenCVRepository
import com.hipercom.recognition.repository.PermissionRepository
import com.hipercom.recognition.ui.components.CameraScreen
import com.hipercom.recognition.ui.components.PermissionScreen
import com.hipercom.recognition.ui.dialogs.PermissionDialogHelper
import com.hipercom.recognition.ui.theme.RecognitionTheme
import com.hipercom.recognition.util.OpenCVCameraHelper
import com.hipercom.recognition.util.openAppSettings
import com.hipercom.recognition.viewmodel.CameraViewModel
import com.hipercom.recognition.viewmodel.CameraViewModelFactory
import com.hipercom.recognition.viewmodel.MainViewModel
import com.hipercom.recognition.viewmodel.MainViewModelFactory
import org.opencv.android.JavaCameraView

class MainActivity : AppCompatActivity() {

    // Crear la instancia de PermissionDialogHelper y el repositorio
    private val permissionDialogs = PermissionDialogHelper()
    private val openCVRepository = OpenCVRepository()
    private val permissionRepository = PermissionRepository(permissionDialogs)

    // Declarar el CameraViewModel y el OpenCVCameraHelper sin inicializarlos
    private lateinit var javaCameraView: JavaCameraView
    private lateinit var cameraViewModel: CameraViewModel
    private lateinit var cameraLauncher: OpenCVCameraHelper

    // Usar viewModels con la fábrica para crear el MainViewModel con las dependencias correctas
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(permissionRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar el CameraViewModel y OpenCVCameraHelper aquí
        cameraViewModel = ViewModelProvider(
            this, CameraViewModelFactory(OpenCVRepository())
        ).get(CameraViewModel::class.java)
        cameraLauncher = OpenCVCameraHelper(cameraViewModel)

        setContent {
            RecognitionTheme {
                PermissionScreen(
                    viewModel = viewModel,
                    onPermissionDenied = { openAppSettings(this) })
            }
        }

        setupCameraView()

        viewModel.permissionsState.observe(this) { state ->
            when (state) {
                PermissionState.GRANTED -> {
                    // Continuar con la inicialización
                    setContent {
                        RecognitionTheme {
                            CameraScreen(javaCameraView)
                        }
                    }
                }

                PermissionState.DENIED, PermissionState.PERMANENTLY_DENIED -> {
                    Toast.makeText(this, R.string.permission_denied_explanation, Toast.LENGTH_LONG)
                        .show()
                    if (state == PermissionState.PERMANENTLY_DENIED) {
                        openAppSettings(this)
                    }
                }
            }
        }

        // Solicitar permisos solo si es necesario
        if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            explainPermissionNeed()
        }
        viewModel.checkPermissions(this)
    }

    private fun explainPermissionNeed() {
        Toast.makeText(this, R.string.camera_permission_explanation, Toast.LENGTH_LONG).show()
    }

    private fun setupCameraView() {
        javaCameraView = JavaCameraView(this, -1).apply {
            setCvCameraViewListener(cameraLauncher)
            visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        if (::javaCameraView.isInitialized) {
            javaCameraView.enableView()
        }
    }

    override fun onPause() {
        super.onPause()
        if (::javaCameraView.isInitialized) {
            javaCameraView.disableView()
        }
    }

}
