package com.sneha.weather.ui.composables.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.sneha.weather.ui.theme.Typography

/**
 * Created by Sneha on 20-08-2024.
 */
@Composable
fun CommonAlertDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String? = null,
    dialogText: String,
    positiveText: String,
    negativeText: String? = null
) {
    AlertDialog(
        modifier = modifier.width(400.dp),
        properties = DialogProperties(dismissOnClickOutside = false),
        containerColor = Color.White,
        title = {
            if (dialogTitle != null) {
                Text(
                    text = dialogTitle,
                    style = Typography.titleLarge.merge(fontWeight = FontWeight.SemiBold)
                )
            }
        },
        text = {
            Text(
                text = dialogText,
                style = Typography.titleMedium
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(
                    positiveText,
                    style = Typography.titleMedium.merge(fontWeight = FontWeight.SemiBold)
                )
            }
        },
        dismissButton = {
            if (negativeText != null) {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(
                        negativeText,
                        style = Typography.titleMedium
                    )
                }
            }
        }
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewCommonAlertDialog() {

}