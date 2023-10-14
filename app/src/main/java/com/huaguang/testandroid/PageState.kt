package com.huaguang.testandroid

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

// TODO: 这里可能要重构
data class RecordPageState(
    val regulatorBarShow: MutableState<Boolean> = mutableStateOf(false),
    val recordMode: MutableState<Boolean> = mutableStateOf(true),
    val buttonsBarShow: MutableState<Boolean> = mutableStateOf(true),
    val currentType: MutableState<CurrentType?> = mutableStateOf(null),
)
