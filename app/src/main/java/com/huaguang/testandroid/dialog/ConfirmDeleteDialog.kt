package com.huaguang.testandroid.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun ConfirmDeleteDialog(
    deleteDialogState: MutableState<DeleteDialogState>,
    onDismiss: () -> Unit = { deleteDialogState.value = DeleteDialogState.Hidden },
) {
    deleteDialogState.value.apply {
        if (!show) return

        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = "删除确认")
            },
            text = {
                Text(text = "你确定要删除这个${target}吗？")
            },
            confirmButton = {
                Button(onClick = {
                    onConfirm()
                    onDismiss()
                }) {
                    Text("确定")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("取消")
                }
            }
        )
    }
}
