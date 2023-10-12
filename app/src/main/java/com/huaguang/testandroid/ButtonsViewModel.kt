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
    var index = 0

    fun onMainStartClick() {
        inputState.show.value = true
        buttonsBarState.value = ButtonsBarState.AllDisplay
        cursor.value = CurrentType.MAIN

        // 测试……
        val internalEvent = InternalEvent(
            startTime = LocalDateTime.now(),
            interval = 12
        )
        events.add(internalEvent)

    }

    fun onAddClick() {
        inputState.show.value = true
        internalItemState.apply {
            intervalButtonShow.value = true
            supplementButtonShow.value = false
        }
        
        inputState.type.value = EventType.ADD
        // 按钮的状态放在最后，有可能很慢！todo: 不是按钮的状态变化慢，是双击导致的触发慢，这里必须优化！！！
        buttonsBarState.value = ButtonsBarState.AddOverDisplay
        cursor.value = CurrentType.ADD

        index++
        val internalEvent2 = InternalEvent(
            startTime = LocalDateTime.now(),
            interval = 3
        )
        events.add(internalEvent2)
    }

    fun onAddButtonDoubleClick() {
        // 下边的逻辑只会在 “新增结束” 下执行（还是添加状态，cursor 不会变化）
        buttonsBarState.value = ButtonsBarState.AddOverDisplay

    }

    fun onAddOverClick() {
        buttonsBarState.value = ButtonsBarState.AllDisplay
        internalItemState.apply {
            supplementButtonShow.value = false
            intervalButtonShow.value = false
        }
        cursor.value = CurrentType.MAIN

        events[index] = events[index].copy(
            endTime = LocalDateTime.now(),
            duration = Duration.ofMinutes(30)
        )
    }

    fun onInsertClick() {
        // TODO: 和 Add 公用的状态 
        inputState.show.value = true
        internalItemState.apply {
            intervalButtonShow.value = true
            supplementButtonShow.value = false
        }
        
        inputState.type.value = EventType.INSERT
        buttonsBarState.value = ButtonsBarState.InsertOverDisplay
        cursor.value = CurrentType.INSERT

        index++
        val internalEvent3 = InternalEvent(
            startTime = LocalDateTime.now(),
            interval = 77
        )
        events.add(internalEvent3)
    }

    fun onInsertOverClick() {
        // TODO: 和 Add 共用的状态 
        buttonsBarState.value = ButtonsBarState.AllDisplay
        internalItemState.apply {
            supplementButtonShow.value = false
            intervalButtonShow.value = false
        }
        cursor.value = CurrentType.MAIN

        // TODO: 甚至结束的过程都是一样的？ 
        events[index] = events[index].copy(
            endTime = LocalDateTime.now(),
            duration = Duration.ofMinutes(30)
        )
    }

    fun onStopButtonClick() {
        buttonsBarState.value = ButtonsBarState.Default
        internalItemState.supplementButtonShow.value = false
        cursor.value = null

        // 测试……
        events[0] = events[0].copy(
            endTime = LocalDateTime.now()
        )
    }

    fun onStopButtonDoubleClick() {
        // 下边的逻辑只在 cursor.value == CurrentType.MAIN 下进行，为 null 的时候结束图标按钮根本不会显示；
        // 也就是说下边的逻辑要处理的是当前主题事件的结束以及下一主题事件的开始
        buttonsBarState.value = ButtonsBarState.AllDisplay
        cursor.value = CurrentType.MAIN
    }

    fun undoButtonClick() {
        TODO("Not yet implemented")
    }

    
    fun onConfirmButtonClick(text: String) {
        val list = text.split("\n")
        val name = list.first()
        val remark = if (list.size == 1) null else list.last()

        inputState.show.value = false
        internalItemState.apply {
            supplementButtonShow.value = true
            intervalButtonShow.value = false
        }

        events[index] = events[index].copy(
            name = name,
            remark = remark,
            type = inputState.type.value,
            label = "标签",
        )

    }

}