package com.huaguang.testandroid.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huaguang.testandroid.cache.CategoryCache
import com.huaguang.testandroid.classifier.ClassifierFactory
import com.huaguang.testandroid.classifier.KeywordClassifier
import com.huaguang.testandroid.data.entities.Event
import com.huaguang.testandroid.data.repositories.CategoryRepository
import com.huaguang.testandroid.data.repositories.EventRepository
import com.huaguang.testandroid.data.repositories.MainRepository
import com.huaguang.testandroid.dtos.EventInput
import com.huaguang.testandroid.utils.getAdjustedDate
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
    private var currentEventId: Long? = null

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
     * 插入预置的类属和对应的关键词
     */
    fun insertPresetData() {
        viewModelScope.launch(Dispatchers.IO) {
            val presetCategories = listOf(
                "常务" to listOf("煮吃", "买吃", "热吃", "洗澡", "洗漱", "厕", "外吃",  "吃晚饭", "买菜", "晾衣", "拿快递", "理发"),
                "家人" to listOf("老妈", "爷爷", "老爸"),
                "休息" to listOf("睡", "躺歇", "眯躺", "远观", "午休"),
                "锻炼" to listOf("慢跑", "俯卧撑"),
                "交际" to listOf("聊天", "通话", "鸿", "铮", "师父", "小洁", "小聚", "回复"),
                "框架" to listOf("财务", "收集库", "时间统计", "清整", "规划", "省思", "统析", "仓库", "预算",
                    "纸上统计", "思考", "草思", "总结", "前路", "探用"),
                "前端" to listOf("HTML", "CSS", "JavaScript", "前端", "Vue", "React"),
                "网络" to listOf("网络是怎样连接的", "图解网络"),
                "Spring" to listOf("spring", "SV", "Spring", "sv"),
                "时光流" to listOf("时光流", "开发", "重构", "修bug", "设计"),
                "探索" to listOf("探索", "逛鱼皮星球", "玩转星球",),
                "休闲" to listOf("散步", "打字", "抽烟", "吹风", "休闲", "续观", "吃水果"),
                "违破" to listOf("躺刷", "贪刷", "朋友圈", "刷视频", "QQ空间", "Q朋抖",
                    "刷手机", "躺思", "刷抖音", "动态", "贪观"),
                "xxx" to listOf("xxx", "泄", "淫",),
            )
            presetCategories.forEach {
                val categoryName = it.first
                val keywords = it.second
                mainRepository.insertCategoryAndKeywords(categoryName, keywords)
            }
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
    private suspend fun getCategoryId(classificationCriteria: String): Long? {
        val categoryName = classifier.classify(classificationCriteria)
        return categoryRepository.retrieveCategoryId(categoryName)
    }

}
