package com.huaguang.testandroid

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class SharedState(
    val currentType: MutableState<CurrentType?> = mutableStateOf(null),
)