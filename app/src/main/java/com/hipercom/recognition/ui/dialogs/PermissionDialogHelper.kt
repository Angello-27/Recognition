package com.hipercom.recognition.ui.dialogs

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.hipercom.recognition.R
import com.hipercom.recognition.util.PermissionDialogs
import com.karumi.dexter.PermissionToken

class PermissionDialogHelper : PermissionDialogs {
    override fun showRationaleDialog(context: Context, token: PermissionToken) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.dialog_permission_title))
            .setMessage(context.getString(R.string.dialog_permission_message))
            .setIcon(R.mipmap.ic_launcher)
            .setPositiveButton(context.getString(R.string.dialog_permission_positive_button)) { dialog, _ ->
                token.continuePermissionRequest()
                dialog.dismiss()
            }
            .setNegativeButton(context.getString(R.string.dialog_permission_negative_button)) { dialog, _ ->
                token.cancelPermissionRequest()
                dialog.dismiss()
            }
            .setOnDismissListener {
                token.cancelPermissionRequest()
            }
            .show()
    }
}