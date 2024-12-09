package com.hipercom.recognition.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hipercom.recognition.model.PermissionState
import com.hipercom.recognition.repository.PermissionRepository
import com.hipercom.recognition.ui.components.PermissionScreen
import com.hipercom.recognition.ui.dialogs.PermissionDialogHelper
import com.hipercom.recognition.util.openAppSettings
import com.hipercom.recognition.viewmodel.MainViewModel
import com.hipercom.recognition.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    // Crear la instancia de PermissionDialogHelper y el repositorio
    private val permissionDialogs = PermissionDialogHelper()
    private val permissionRepository = PermissionRepository(permissionDialogs)

    // Usar viewModels con la fábrica para crear el ViewModel con las dependencias correctas
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(permissionRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PermissionScreen(
                viewModel = viewModel,
                onPermissionDenied = { openAppSettings(this) }
            )
        }

        viewModel.permissionsState.observe(this) { state ->
            when (state) {
                PermissionState.GRANTED -> {
                    // Continuar con la inicialización
                }
                PermissionState.DENIED -> {
                    Toast.makeText(this, "You need to grant all permissions to use the app", Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.checkPermissions(this)
    }

}
