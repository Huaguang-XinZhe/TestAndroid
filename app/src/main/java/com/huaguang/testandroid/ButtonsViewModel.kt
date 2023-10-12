package com.huaguang.testandroid

import androidx.lifecycle.ViewModel

class ButtonsViewModel(
    val pageState: RecordPageState = RecordPageState()
) : ViewModel() {
    val internalItemState = pageState.internalItemState
    val buttonsBarState = pageState.buttonsBarState

    fun onMainStartClick() {
        buttonsBarState.value = ButtonsBarState.AllDisplay
    }

    fun undoButtonClick() {
        TODO("Not yet implemented")
    }

    fun onAddButtonClick(text: String) {
        buttonsBarState.value = if (text == "新增") {
            ButtonsBarState.AddOverDisplay
        } else ButtonsBarState.AllDisplay // 也只能是新增结束了
    }

    fun onInsertButtonClick(text: String) {
        buttonsBarState.value = if (text == "插入") {
            ButtonsBarState.InsertOverDisplay
        } else ButtonsBarState.AllDisplay // 也只能是插入结束了
    }

    fun onAddButtonDoubleClick() {
        buttonsBarState.value = ButtonsBarState.AddOverDisplay
    }

    fun onStopButtonClick() {
        buttonsBarState.value = ButtonsBarState.Default
    }

    fun onStopButtonDoubleClick() {
        buttonsBarState.value = ButtonsBarState.AllDisplay
    }



}