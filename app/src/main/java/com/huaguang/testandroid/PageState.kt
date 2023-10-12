package com.huaguang.testandroid

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class RecordPageState(
    val regulatorBarShow: MutableState<Boolean> = mutableStateOf(false),
    val recordBlockShow: MutableState<Boolean> = mutableStateOf(true),
    val buttonsBarState: MutableState<ButtonsBarState> = mutableStateOf(ButtonsBarState.Default),
    val internalItemState: MutableState<InternalItemState> = mutableStateOf(InternalItemState.Default)
)
