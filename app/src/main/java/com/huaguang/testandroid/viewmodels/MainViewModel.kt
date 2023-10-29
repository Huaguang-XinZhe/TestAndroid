package com.huaguang.testandroid.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huaguang.testandroid.cache.CategoryCache
import com.huaguang.testandroid.classifier.ClassifierFactory
import com.huaguang.testandroid.classifier.KeywordClassifier
import com.huaguang.testandroid.data.entities.Event
import com.huaguang.testandroid.data.repositories.CategoryRepository
import com.huaguang.testandroid.data.repositories.EventRepository
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
    private val eventRepository: EventRepository,
    private val categoryRepository: CategoryRepository,
    private val classifierFactory: ClassifierFactory,
) : ViewModel() {

    private lateinit var classifier: KeywordClassifier

    init {
        viewModelScope.launch(Dispatchers.IO) {
            // 初始化类属缓存
            CategoryCache.initCache(categoryRepository)
            // 初始化分类器
            classifier = classifierFactory.createKeywordClassifier()
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
        val categoryId = getCategoryId(eventName)

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

        val categoryId = getCategoryId(classificationCriteria)

        // 更新事件
        eventRepository.updNameRemarkAndCategory(eventInput.eventId, name, remark, categoryId)
    }

    suspend fun stopCurrentEvent() {
        val now = LocalDateTime.now()
        lastEventEndTime = now
        val duration = Duration.between(lastEventEndTime, now)

        currentEventId?.let {
            eventRepository.updEndTimeAndDuration(it, now, duration)
        }
    }


    /**
     * 获取事件类属 ID。
     *
     * 先用分类器对传入的分类材料进行分类，然后去缓存中找 id，
     * 如果缓存中没有，则在数据库中插入一个新的类属，并返回其 ID，然后更新缓存。
     *
     * @param classificationCriteria 分类材料/依据
     */
    private suspend fun getCategoryId(classificationCriteria: String): Int? {
        val categoryName = classifier.classify(classificationCriteria)
        return categoryRepository.retrieveCategoryId(categoryName)
    }

}
