package com.huaguang.testandroid

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ButtonsViewModel @Inject constructor(
    val pageState: RecordPageState,
    val internalItemState: InternalItemState,
    val inputState: InputState,
) : ViewModel() {
    private val buttonsBarState = pageState.buttonsBarState
    val cursor = pageState.currentType

    val events = mutableStateListOf<InternalEvent>()

    fun onMainStartClick() {
        buttonsBarState.value = ButtonsBarState.AllDisplay
        inputState.show.value = true

        // 测试……
        val internalEvent = InternalEvent(
            startTime = LocalDateTime.now(),
            interval = 12
        )
        events.add(internalEvent)

    }

    fun undoButtonClick() {
        TODO("Not yet implemented")
    }

    fun onAddButtonDoubleClick() {
        // 下边的逻辑只会在 “新增结束” 下执行
        buttonsBarState.value = ButtonsBarState.AddOverDisplay


    }

    fun onStopButtonClick() {
        buttonsBarState.value = ButtonsBarState.Default


        // 测试……
        val internalEvent = events[0]
        internalEvent.endTime = LocalDateTime.now()
    }

    fun onStopButtonDoubleClick() {
        // 下边的逻辑只在 cursor.value == CurrentType.MAIN 下进行，为 null 的时候结束图标按钮根本不会显示；
        // 也就是说下边的逻辑要处理的是当前主题事件的结束以及下一主题事件的开始
        buttonsBarState.value = ButtonsBarState.AllDisplay
    }

    fun onAddClick() {
        ButtonsBarState.AddOverDisplay

        val internalEvent2 = InternalEvent(
            name = "a14f@sgf11zp.com",
            duration = Duration.ofMinutes(63),
            label = "核心",
            startTime = LocalDateTime.now(),
        )
        events.add(internalEvent2)
    }

    fun onAddOverClick() {
        ButtonsBarState.AllDisplay

        val internalEvent2 = events[1]
        internalEvent2.endTime = LocalDateTime.now()
    }

    fun onInsertClick() {
        ButtonsBarState.InsertOverDisplay

        val internalEvent3 = InternalEvent(
            name = "森森的33.190.80.243",
            type = EventType.INSERT,
            duration = Duration.ofMinutes(12),
            startTime = LocalDateTime.now(),
            interval = 77
        )
        events.add(internalEvent3)
    }

    fun onInsertOverClick() {
        ButtonsBarState.AllDisplay

        val internalEvent3 = events[2]
        internalEvent3.endTime = LocalDateTime.now()
    }

    fun onConfirmButtonClick(text: String) {
        inputState.apply {
            newName.value = text
            show.value = false
        }
        internalItemState.supplementButtonShow.value = true

        val mainEvent = events[0]
        val newMainEvent = mainEvent.copy(
            name = "贵州省毕节市赫章县水塘堡彝族苗族乡",
            remark = "在,说, 随机一段废话对我的意义x不仅仅是一个重大的",
            type = EventType.MAIN,
            label = "当前核心",
        )
        // 替换第一个列表第一个位置的元素
        events[0] = newMainEvent

    }


}