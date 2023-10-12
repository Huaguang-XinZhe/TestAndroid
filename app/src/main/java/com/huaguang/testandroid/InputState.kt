package com.huaguang.testandroid

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class InputState(
    val show: MutableState<Boolean> = mutableStateOf(false),
    val newName: MutableState<String> = mutableStateOf("")
)
