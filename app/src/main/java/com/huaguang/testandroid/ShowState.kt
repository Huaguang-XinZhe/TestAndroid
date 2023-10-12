package com.huaguang.testandroid

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ShowState(
    val regulatorBarShow: MutableState<Boolean> = mutableStateOf(false),
    val buttonsBarShow: MutableState<Boolean> = mutableStateOf(true),
    val recordBlockShow: MutableState<Boolean> = mutableStateOf(true),
    val intervalButtonShow: MutableState<Boolean> = mutableStateOf(false),
    val internalStartTimeShow: MutableState<Boolean> = mutableStateOf(false),
    val internalEndTimeShow: MutableState<Boolean> = mutableStateOf(false),
    val supplementButtonShow: MutableState<Boolean> = mutableStateOf(true),
)


sealed class RecordBlockState(
    val startTimeShow: MutableState<Boolean> = mutableStateOf(false),
    val endTimeShow: MutableState<Boolean> = mutableStateOf(false),
    val supplementButtonShow: MutableState<Boolean> = mutableStateOf(false),
    val intervalButtonShow: MutableState<Boolean> = mutableStateOf(true),
) {
    object Default : RecordBlockState()
}