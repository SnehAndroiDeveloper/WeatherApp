package com.sneha.weather.ui.composables.components

import androidx.compose.foundation.background
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.sneha.weather.R

/**
 * Created by Sneha on 19-08-2024.
 */
@Composable
fun ShowAlertDialog(message: String, onDismiss: () -> Unit) {
    AlertDialog(
        text = {
            Text(text = message, fontSize = 14.sp)
        }, onDismissRequest = {
            onDismiss()
        }, confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = stringResource(id = R.string.ok), fontSize = 18.sp)
            }
        })
}

@Preview
@Composable
fun PreviewShowAlertDialog() {
    ShowAlertDialog("Sneha") {

    }
}