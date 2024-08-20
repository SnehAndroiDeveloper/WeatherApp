package com.sneha.weather.ui.composables.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.sneha.weather.R

/**
 * Created by Sneha on 19-08-2024.
 */
@Composable
fun NoInternetAlert(message: String, onDismiss: () -> Unit) {
    CommonAlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        onConfirmation = {
            onDismiss()
        },
        dialogText = message,
        positiveText = stringResource(id = R.string.ok)
    )
}

@Preview
@Composable
fun PreviewNoInternetAlert() {
    NoInternetAlert("Sneha") {

    }
}