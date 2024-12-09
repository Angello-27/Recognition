package com.hipercom.recognition.ui.activities

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.hipercom.recognition.R
import com.hipercom.recognition.model.PermissionState
import com.hipercom.recognition.repository.PermissionRepository
import com.hipercom.recognition.ui.components.CameraScreen
import com.hipercom.recognition.ui.components.PermissionScreen
import com.hipercom.recognition.ui.dialogs.PermissionDialogHelper
import com.hipercom.recognition.ui.theme.RecognitionTheme
import com.hipercom.recognition.util.openAppSettings
import com.hipercom.recognition.viewmodel.CameraViewModel
import com.hipercom.recognition.viewmodel.MainViewModel
import com.hipercom.recognition.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {

    // Crear la instancia de PermissionDialogHelper y el repositorio
    private val permissionDialogs = PermissionDialogHelper()
    private val permissionRepository = PermissionRepository(permissionDialogs)

    // Usar viewModels con la fábrica para crear el MainViewModel con las dependencias correctas
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(permissionRepository)
    }

    // Instanciar CameraViewModel directamente
    private val cameraViewModel: CameraViewModel by viewModels()

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { _: Bitmap? ->
        // Aquí manejas el Bitmap, tal como mostrarlo en una ImageView o guardar el estado
        // Asegúrate de manejar el caso en que bitmap sea null
        Log.d("MainActivity", "Received bitmap")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RecognitionTheme() {
                PermissionScreen(
                    viewModel = viewModel,
                    onPermissionDenied = { openAppSettings(this) }
                )
            }
        }

        viewModel.permissionsState.observe(this) { state ->
            when (state) {
                PermissionState.GRANTED -> {
                    // Continuar con la inicialización
                    setContent {
                        RecognitionTheme {
                            CameraScreen(cameraViewModel, cameraLauncher)
                        }
                    }
                }
                PermissionState.DENIED, PermissionState.PERMANENTLY_DENIED -> {
                    Toast.makeText(this, R.string.permission_denied_explanation, Toast.LENGTH_LONG).show()
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

}
