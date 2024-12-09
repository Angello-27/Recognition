package com.hipercom.recognition.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.hipercom.recognition.model.PermissionState
import com.hipercom.recognition.viewmodel.MainViewModel

@Composable
fun PermissionScreen(
    viewModel: MainViewModel,
    onPermissionDenied: () -> Unit
) {
    LaunchedEffect(viewModel.permissionsState) {
        viewModel.permissionsState.observeForever { state ->
            if (state == PermissionState.DENIED) {
                onPermissionDenied()
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Text("Checking permissions...")
    }
}
