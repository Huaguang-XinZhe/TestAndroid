package com.huaguang.testandroid

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class InputState(
    val show: MutableState<Boolean> = mutableStateOf(false),
    // 这个测试的时候暂时用不着
    val newName: MutableState<String> = mutableStateOf(""),
)
