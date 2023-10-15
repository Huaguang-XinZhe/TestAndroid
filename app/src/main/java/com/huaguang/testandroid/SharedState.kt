package com.huaguang.testandroid

import androidx.lifecycle.MutableLiveData

data class SharedState(
    val toastMessage: MutableLiveData<String?> = MutableLiveData(null)
)
