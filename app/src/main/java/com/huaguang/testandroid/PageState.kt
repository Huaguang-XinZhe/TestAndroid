package com.huaguang.testandroid

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

// TODO: 这里可能要重构
data class RecordPageState(
    val regulatorBarShow: MutableState<Boolean> = mutableStateOf(false),
    val recordBlockShow: MutableState<Boolean> = mutableStateOf(true),
    val currentType: MutableState<CurrentType?> = mutableStateOf(null),
    val buttonsBarState: MutableState<ButtonsBarState> = mutableStateOf(ButtonsBarState.Default),
)
