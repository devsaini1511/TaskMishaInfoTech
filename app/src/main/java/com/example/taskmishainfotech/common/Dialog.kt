package com.example.taskmishainfotech.common

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.taskmishainfotech.databinding.DialogPermissionRequestBinding
import com.example.taskmishainfotech.databinding.DialogShowDataBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


fun Fragment.removeItemDialog(
    permissionType: String,
    onDenied: (() -> Unit)? = null,
    onAllow: (() -> Unit) = { },
) {
    val alertBinding = DialogPermissionRequestBinding.inflate(layoutInflater)
        .apply {
            name = permissionType
            description = "Are you sure to delete "
        }

    val alertDialog = MaterialAlertDialogBuilder(requireContext())
        .setView(alertBinding.root)
        .setCancelable(false)
        .create()

    val onAllowClick: (View) -> Unit = {

        onAllow()
        if (alertDialog.isShowing)
            alertDialog.dismiss()
    }
    alertBinding.allowButton.setOnClickListener(onAllowClick)
    alertBinding.denyButton.setOnClickListener {
        if (alertDialog.isShowing) {
            alertDialog.dismiss()
            if (onDenied != null) {
                onDenied()
            } else findNavController().navigateUp()
        }
    }
    alertDialog.show()


}


fun Fragment.showData(
    permissionType: String,
    detail: String,
    imageUrl: String,
    onDenied: (() -> Unit)? = null,
) {
    val alertBinding = DialogShowDataBinding.inflate(layoutInflater)
        .apply {
            name = permissionType
            description = detail
            image = imageUrl
        }

    val alertDialog = MaterialAlertDialogBuilder(requireContext())
        .setView(alertBinding.root)
        .setCancelable(false)
        .create()

    alertBinding.previewImageView.load(imageUrl)
    alertBinding.denyButton.setOnClickListener {
        if (alertDialog.isShowing) {
            alertDialog.dismiss()
            if (onDenied != null) {
                onDenied()
            } else findNavController().navigateUp()
        }
    }
    alertDialog.show()


}
