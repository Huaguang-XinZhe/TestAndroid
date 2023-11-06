package com.huaguang.testandroid

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huaguang.testandroid.buttons_bar.ButtonsBarState
import com.huaguang.testandroid.cache.TimeCache
import com.huaguang.testandroid.input_field.InputState
import com.huaguang.testandroid.record_block.CurrentType
import com.huaguang.testandroid.record_block.EventType
import com.huaguang.testandroid.record_block.InternalEvent
import com.huaguang.testandroid.time_label.TimeLabelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class RecordPageViewModel @Inject constructor(
    val buttonsBarState: MutableState<ButtonsBarState>,
//    val recordBlockState: RecordBlockState,
    val inputState: InputState,
    private val timeCache: TimeCache,
    val pageState: RecordPageState,
    private val sharedState: SharedState,
) : ViewModel() {
    val cursor = pageState.currentType
    val events = mutableStateListOf<InternalEvent>()
    private var job: Job? = null
    var currentTimeLabelState: TimeLabelState? = null

    fun onMainStartClick() {
        inputState.show.value = true
        buttonsBarState.value = ButtonsBarState.AllDisplay
        cursor.value = CurrentType.MAIN

        createEvent(
            lastTime = timeCache.mainEnd ?: LocalDateTime.now(),
        )

    }

    fun onAddClick(startTime: LocalDateTime = LocalDateTime.now()) {
        onMiddleButtonClick(EventType.ADD, startTime)
    }

    fun onAddButtonLongClick() {
        // 下边的逻辑只会在 “新增结束” 下执行（还是添加状态，cursor 不会变化）
//        buttonsBarState.value = ButtonsBarState.AddOverDisplay

        stopCurrentEvent()
        onAddClick()
    }

    fun onAddOverClick() {
        onMiddleButtonOverClick()
    }

    fun onInsertClick(startTime: LocalDateTime = LocalDateTime.now()) {
        onMiddleButtonClick(EventType.INSERT, startTime)
    }

    fun onInsertOverClick() {
        onMiddleButtonOverClick()
    }

    fun onStopButtonClick() {
        buttonsBarState.value = ButtonsBarState.Default

        if (cursor.value != CurrentType.MAIN) {
            stopCurrentEvent()
        }
        stopMainEvent()

        cursor.value = null
    }

    fun onStopButtonLongClick() {
        // 下边的逻辑只在 cursor.value == CurrentType.MAIN 下进行，为 null 的时候结束图标按钮根本不会显示；
        // 也就是说下边的逻辑要处理的是当前主题事件的结束以及下一主题事件的开始
//        buttonsBarState.value = ButtonsBarState.AllDisplay
//        cursor.value = CurrentType.MAIN

        stopMainEvent()
        onMainStartClick()
    }
    
    fun onConfirmButtonClick(text: String) {
        inputState.show.value = false
        updateName(text)
        currentTimeLabelState?.let { onTimeUpdated(it) } // 确认后 1.2 秒自动更新状态（针对不调整的情境）
    }

    fun onSButtonClick() {
        // 补计按钮点击，触发 Add 补计，间隔 1 分钟
        supplement()
    }

    fun onSButtonLongClick() {
        // 补计按钮长按，触发 Insert 补计，间隔 1 分钟
        supplement(isAdd = false)
    }

    fun undoButtonClick() {
        TODO("Not yet implemented")
    }

    private fun createEvent(
        lastTime: LocalDateTime,
        startTime: LocalDateTime = LocalDateTime.now(),
        type: EventType = EventType.MAIN,
    ) {
        val internalEvent = InternalEvent(
            startTime = startTime,
            interval = ChronoUnit.MINUTES.between(lastTime, startTime).toInt() + 10,
            type = type,
        )
        val now = LocalDateTime.now()

        if (type == EventType.MAIN) {
            timeCache.mainStart = now
        } else {
            timeCache.currentStart = now
        }

        events.add(internalEvent)
    }

    private fun stopCurrentEvent() {
        val now = LocalDateTime.now()
        timeCache.currentEnd = now
        events[timeCache.index] = events[timeCache.index].copy(
            endTime = now,
            duration = Duration.between(events[timeCache.index].startTime, now)
        )
    }

    private fun stopMainEvent() {
        val now = LocalDateTime.now()
        timeCache.mainEnd = now
        events[0] = events[0].copy(
            endTime = now,
            duration = Duration.between(timeCache.mainStart, now)
        )
    }

    private fun updateName(text: String) {
        val list = text.split("\n")
        val name = list.first()
        val remark = if (list.size == 1) null else list.last()

        events[timeCache.index] = events[timeCache.index].copy(
            name = name,
            remark = remark,
            label = "标签", // TODO: 根据名称去判断
        )
    }

    private fun onMiddleButtonClick(type: EventType, startTime: LocalDateTime) {
        inputState.show.value = true

        if (type == EventType.ADD) {
            buttonsBarState.value = ButtonsBarState.AddOverDisplay
            cursor.value = CurrentType.ADD
        } else { // 也只能是插入了
            buttonsBarState.value = ButtonsBarState.InsertOverDisplay
            cursor.value = CurrentType.INSERT
        }

        val lastTime = if (timeCache.index == 0) {
            timeCache.mainStart!! // 能点击新增，主题时间的开始时间一定不为 null
        } else {
            // 这里的 currentEnd 其实是上一事件的结束时间，因为在创建新时间的时候没有把它更新为 null
            timeCache.currentEnd!! // 上一非主题事件没有结束是不能点击 Add 按钮的，所以能点击的时候，endTime 一定非空
        }
        createEvent(
            lastTime = lastTime,
            type = type,
            startTime = startTime
        )
        timeCache.index++
    }

    private fun onMiddleButtonOverClick() {
        buttonsBarState.value = ButtonsBarState.AllDisplay
        cursor.value = CurrentType.MAIN

        stopCurrentEvent()
    }

    private fun supplement(isAdd: Boolean = true) {
        val adjustedStartTime = if (timeCache.index == 0) {
            timeCache.mainStart!!.plusMinutes(1)
        } else {
            // 这个 currentEnd 在当前事项还没有结束的时候就是上一个事件的结束时间
            timeCache.currentEnd!!.plusMinutes(1)
        }

        if (isAdd) {
            onAddClick(adjustedStartTime)
        } else { // 也只能是插入了
            onInsertClick(adjustedStartTime)
        }
    }

    fun onAdjustButtonClick(value: Long) {
        if (currentTimeLabelState == null || currentTimeLabelState!!.isSelected.value.not()) return // 没选中，点击无效！

        currentTimeLabelState!!.dynamicTime.value =
            currentTimeLabelState!!.dynamicTime.value.plusMinutes(value)

        onTimeUpdated(currentTimeLabelState!!)
    }

    private fun onTimeUpdated(labelState: TimeLabelState) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(1200) // 延迟 1.2 秒
            Log.d(TAG, "onTimeUpdated: labelState = $labelState")
            labelState.apply {
                // 更新初始时间
                initialTime = labelState.dynamicTime.value
                // 取消选中
                isSelected.value = false
                // 隐藏（非主题事件）
                isShow.value = eventType == EventType.MAIN
                // 切换底部按钮栏
                toggleBar()
            }
            // 如果时间变化了，才显示 toast
            if (labelState.dynamicTime.value != labelState.initialTime) {
                sharedState.toastMessage.value = "时间调整成功"
            }
        }
    }

    /**
     * regulatorBar 和 buttonsBar 切换函数
     */
    fun toggleBar() {
        Log.d(TAG, "toggleBar: 执行！")
        pageState.regulatorBarShow.value = pageState.regulatorBarShow.value.not()
        pageState.buttonsBarShow.value = pageState.buttonsBarShow.value.not()
    }

}