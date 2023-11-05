package com.huaguang.testandroid

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.huaguang.testandroid.record_block.CurrentType

// TODO: 这里可能要重构
data class RecordPageState(
    val recordMode: MutableState<Boolean> = mutableStateOf(true),
    val currentType: MutableState<CurrentType?> = mutableStateOf(null),
    val buttonsBarShow: MutableState<Boolean> = mutableStateOf(true),
    val regulatorBarShow: MutableState<Boolean> = mutableStateOf(false),
)
