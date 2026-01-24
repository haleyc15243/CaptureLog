package com.rhc.capturelog.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LoadingContent(
    modifier: Modifier = Modifier.fillMaxSize()
) {
    Box(modifier = modifier) {
        CircularProgressIndicator()
    }
}

@Composable
fun OkAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            TextButton(onDismiss) {
                Text(text = "OK")
            }
        },
    )
}

@Preview
@Composable
private fun OkAlertDialogPreview() {
    OkAlertDialog(
        title = "Title",
        message = "Message",
        onDismiss = {}
    )
}