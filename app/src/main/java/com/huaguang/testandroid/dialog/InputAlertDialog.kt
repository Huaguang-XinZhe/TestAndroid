package com.huaguang.testandroid.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputAlertDialog(
    dialogState: MutableState<DialogState>,
    onDismiss: () -> Unit = { dialogState.value = DialogState.Hidden },
) {
    dialogState.value.apply {
        if (!show) return

        // 每次重组都会重新调用，以获取最新的初始值
        val initialTextFieldValue = TextFieldValue(
            text = initialValue,
            selection = TextRange(initialValue.length)
        )
        val textFieldValue = remember { mutableStateOf(initialTextFieldValue) }
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            delay(100)
            focusRequester.requestFocus()
        }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = title) },
            text = {
                OutlinedTextField(
                    value = textFieldValue.value,
                    onValueChange = { textFieldValue.value = it },
                    label = { Text(label) },
                    modifier = Modifier.focusRequester(focusRequester)
                )
            },
            confirmButton = {
                Button(onClick = {
                    onConfirm(textFieldValue.value.text)
                    onDismiss()
                }) {
                    Text("确认")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("取消")
                }
            },
            properties = DialogProperties(usePlatformDefaultWidth = false) // 禁用默认宽（高），使之随内容宽（高）度自适应
        )
    }
}