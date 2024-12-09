package com.hipercom.recognition.repository

import android.Manifest
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import com.hipercom.recognition.R
import com.hipercom.recognition.model.PermissionState
import com.hipercom.recognition.util.PermissionDialogs
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class PermissionRepository(private val permissionDialogs: PermissionDialogs) {
    fun checkPermissions(context: Context, liveData: MutableLiveData<PermissionState>) {
        Dexter.withContext(context)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        liveData.postValue(PermissionState.GRANTED)
                    } else {
                        liveData.postValue(PermissionState.DENIED)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    // Muestra un diálogo explicativo aquí
                    permissionDialogs.showRationaleDialog(context, token)
                }
            }).check()
    }
}