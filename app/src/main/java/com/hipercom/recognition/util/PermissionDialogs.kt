package com.hipercom.recognition.util

import android.content.Context
import com.karumi.dexter.PermissionToken

interface PermissionDialogs {
    fun showRationaleDialog(context: Context, token: PermissionToken)
}