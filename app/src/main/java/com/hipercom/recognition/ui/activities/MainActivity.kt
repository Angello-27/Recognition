package com.hipercom.recognition.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.hipercom.recognition.model.PermissionState
import com.hipercom.recognition.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setContent {
            PermissionScreen(viewModel)
        }

        viewModel.permissionsState.observe(this) { state ->
            when (state) {
                PermissionState.GRANTED -> {
                    // Continuar con la inicialización
                }

                else -> {
                    // Mostrar mensaje de permiso denegado
                    Toast.makeText(
                        this,
                        "You need to grant all permissions to use the app",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        viewModel.checkPermissions(this)
    }
}

@Composable
fun PermissionScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Text("Checking permissions...")
        // Puedes expandir este Composable para manejar diferentes estados de UI basados en el estado del permiso
    }
}
