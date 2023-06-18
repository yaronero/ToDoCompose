package com.example.todocompose.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.todocompose.R
import com.example.todocompose.ui.theme.ToDoComposeTheme

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onYesClicked: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onYesClicked()
                        closeDialog()
                    }
                ) {
                    Text(text = stringResource(id = R.string.yes))
                }
            },
            dismissButton = {
                Button(
                    onClick = { closeDialog() }
                ) {
                    Text(text = stringResource(id = R.string.no))
                }
            },
            onDismissRequest = {
                closeDialog()
            }
        )
    }
}

@Composable
@Preview
@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark mode"
)
fun DisplayAlertDialogPreview() {
    ToDoComposeTheme {
        DisplayAlertDialog(
            title = "Dialog",
            message = "Simple dialog",
            openDialog = true,
            closeDialog = {},
            onYesClicked = {}
        )
    }
}