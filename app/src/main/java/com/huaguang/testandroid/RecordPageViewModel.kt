package com.huaguang.testandroid

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
import com.huaguang.testandroid.time_label.TimeLabelType
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
    // 上次选中的时间标签状态
    private var lastTimeLabelState: TimeLabelState? = null
    // 上次时间标签状态的协程
    private var lastTimeLabelJob: Job? = null
    // 当前选中的时间标签状态
    private var currentTimeLabelState: TimeLabelState? = null
    // 当前时间标签状态的协程
    private var currentTimeLabelJob: Job? = null
    // 是否执行共享逻辑的标志，只在 onTimeUpdated 方法内生效（在其内被重置）
    private var hasExecutedSharedLogic = false

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
        // 时间不调整也会自动更新选中状态，来源于这里！！！
        currentTimeLabelState?.let { onTimeUpdated() } // 确认后 1.2 秒自动更新状态（针对不调整的情境）
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

    /**
     * regulatorBar 和 buttonsBar 切换函数
     */
    fun toggleBar() {
        pageState.regulatorBarShow.value = pageState.regulatorBarShow.value.not()
        pageState.buttonsBarShow.value = pageState.buttonsBarShow.value.not()
    }

    /**
     * 时间调整按钮点击回调
     *
     * 无论是当前时间标签还是上一个时间标签，只要选中就能调
     */
    fun onAdjustButtonClick(value: Long) {
        // 上个时间标签
        if (lastTimeLabelState != null && lastTimeLabelState!!.isSelected.value) {
            adjustTime(lastTimeLabelState!!, value)
        }
        // 当前时间标签
        if (currentTimeLabelState != null && currentTimeLabelState!!.isSelected.value) {
            adjustTime(currentTimeLabelState!!, value)
        }
    }

    /**
     * 时间标签选中回调
     */
    fun onTimeSelected(labelState: TimeLabelState) {
        lastTimeLabelState = currentTimeLabelState // 保存上一个选中的时间标签状态
        // 在这里赋值才标准，如果用创建的 state 直接赋值，那在创建之外的选中场景就无法获取到 state 了
        currentTimeLabelState = labelState
        // 只要一选中，就显示调节器，隐藏按钮行（会触发整个页面重组，进而触发当前组件重组）
        pageState.regulatorBarShow.value = true
        pageState.buttonsBarShow.value = false
        // 启动一个判断回调
        onJudgeSelected(labelState)
    }

    /**
     * 时间标签选中后的判断回调。
     *
     * 用户两个时间标签交互的情景。
     * 对于上一个选中的时间标签，什么时候保持选中状态，什么时候取消选中状态。
     */
    private fun onJudgeSelected(labelState: TimeLabelState) {
        // 如果上一个选中的时间标签状态不为空，且当前选中的时间标签状态不为空，且两个时间标签状态不是同一个，那就要判断是否保持选中状态
        if (lastTimeLabelState != null && currentTimeLabelState != null && lastTimeLabelState != currentTimeLabelState) {
            // 如果不应该保持选中状态，就取消上一个选中的时间标签状态
            if (!shouldKeepSelected(lastTimeLabelState!!, currentTimeLabelState!!)) {
                lastTimeLabelState!!.isSelected.value = false
            }
        }
    }

    /**
     * 时间调整的具体逻辑，只要传入状态就能调
     */
    private fun adjustTime(labelState: TimeLabelState, value: Long) {
        labelState.dynamicTime.value = labelState.dynamicTime.value.plusMinutes(value)
        onTimeUpdated()
    }

    /**
     * 时间调整后的回调
     *
     * 取消协程，新建一个，然后更新状态；
     * 一次逻辑：切按钮栏、调节器栏，更新数据库，弹 toast
     */
    private fun onTimeUpdated() {
        // 取消之前的 jobs
        lastTimeLabelJob?.cancel()
        currentTimeLabelJob?.cancel()

        // 重置共享逻辑执行标志
        hasExecutedSharedLogic = false

        // 创建新的 jobs
        lastTimeLabelJob = viewModelScope.launch {
            delay(1200) // 延迟 1.2 秒
            lastTimeLabelState?.let {
                updateLabelState(it)
                executeSharedLogicIfNeeded(it)
            }
        }
        currentTimeLabelJob = viewModelScope.launch {
            delay(1200) // 延迟 1.2 秒
            currentTimeLabelState?.let {
                updateLabelState(it)
                executeSharedLogicIfNeeded(it)
            }
        }
    }


    private fun updateLabelState(timeLabelState: TimeLabelState) {
        timeLabelState.apply {
            // 更新初始时间
            initialTime = timeLabelState.dynamicTime.value
            // 取消选中
            isSelected.value = false
            // 隐藏（非主题事件）
            isShow.value = eventType == EventType.MAIN
        }
    }

    /**
     * 这个方法在 onTimeUpdated 方法的作用域内只会执行一次
     */
    private fun executeSharedLogicIfNeeded(labelState: TimeLabelState) {
        if (!hasExecutedSharedLogic) {
            hasExecutedSharedLogic = true
            // 执行只应该发生一次的逻辑
            toggleBar()
            // 下边的逻辑，如果时间没有调整，就退出
            if (labelState.initialTime == labelState.dynamicTime.value) return
            updateDatabase() // 更新数据库
            sharedState.toastMessage.value = "时间调整成功"
        }
    }

    /**
     * 更新数据库的逻辑
     */
    private fun updateDatabase() {
        // 更新数据库的代码
    }

    companion object {
        private const val TAG = "RecordPageViewModel"
    }

    /**
     * 时间标签是否选中的具体判断逻辑。
     *
     * 如果前后选中的两个时间标签的类型是：
     * 1. “两个结尾 + 一个主一个子”；
     * 2. “两个开头 + 一个主一个子”；
     * 3. “两个子，一个开头一个结尾”；
     * 那么就应该保持选中状态。
     */
    private fun shouldKeepSelected(firstLabel: TimeLabelState, secondLabel: TimeLabelState): Boolean {
        val isBothEnds = firstLabel.type == TimeLabelType.END && secondLabel.type == TimeLabelType.END
        val isBothStarts = firstLabel.type == TimeLabelType.START && secondLabel.type == TimeLabelType.START
        val isOneMainOneSub = firstLabel.eventType != secondLabel.eventType
        val isSubStartAndEnd = firstLabel.eventType.isSub() && secondLabel.eventType.isSub() &&
                ((firstLabel.type == TimeLabelType.START && secondLabel.type == TimeLabelType.END) ||
                        (firstLabel.type == TimeLabelType.END && secondLabel.type == TimeLabelType.START))

        return (isBothEnds && isOneMainOneSub) ||
                (isBothStarts && isOneMainOneSub) ||
                isSubStartAndEnd
    }
}