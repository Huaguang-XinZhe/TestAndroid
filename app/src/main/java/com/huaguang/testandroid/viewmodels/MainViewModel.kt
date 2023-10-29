package com.huaguang.testandroid.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huaguang.testandroid.SharedState
import com.huaguang.testandroid.cache.CategoryCache
import com.huaguang.testandroid.classifier.ClassifierType
import com.huaguang.testandroid.data.entities.Event
import com.huaguang.testandroid.data.repositories.CategoryRepository
import com.huaguang.testandroid.data.repositories.EventRepository
import com.huaguang.testandroid.data.repositories.MainRepository
import com.huaguang.testandroid.dtos.EventInput
import com.huaguang.testandroid.getAdjustedDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val eventRepository: EventRepository,
    private val categoryRepository: CategoryRepository,
    private val sharedState: SharedState
) : ViewModel() {

    init {
        // 初始化类属缓存
        viewModelScope.launch(Dispatchers.IO) {
            CategoryCache.initCache(categoryRepository)
        }
    }

    // 缓存的上一个事件的结束时间
    private var lastEventEndTime: LocalDateTime = LocalDateTime.now() // 初始化为当前时间或其他值
    // 缓存的当前事件的 ID
    private var currentEventId: Int? = null

    suspend fun startNewEvent(eventName: String = "") {
        val now = LocalDateTime.now()
        val eventDate = getAdjustedDate()
        val interval = Duration.between(lastEventEndTime, now).toMinutes().toInt()
        val categoryId = CategoryCache.getIdForCategoryName()

        val event = Event(
            startTime = now,
            eventDate = eventDate,
            interval = interval,
            name = eventName,
            categoryId = categoryId
        )

        currentEventId = eventRepository.insertEvent(event)
    }

    suspend fun updateEventName(eventInput: EventInput) {
        val name = eventInput.name
        val remark = eventInput.remark

        val classificationCriteria = when (eventInput.mode) {
            EventInput.Mode.INPUT -> "$name\n$remark"
            EventInput.Mode.UPDATE -> name
        }

        val categoryName = sharedState.classify(classificationCriteria, ClassifierType.TYPE1)

        // 更新事件
        mainRepository.updateEventWithCategory(eventInput.eventId, name, remark, categoryName)
    }

    suspend fun stopCurrentEvent() {
        val now = LocalDateTime.now()
        lastEventEndTime = now
        val duration = Duration.between(lastEventEndTime, now)

        currentEventId?.let {
            eventRepository.updEndTimeAndDuration(it, now, duration)
        }
    }




}
