package com.huaguang.testandroid.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huaguang.testandroid.data.entities.Category
import com.huaguang.testandroid.data.entities.Keyword
import com.huaguang.testandroid.data.entities.Tag
import com.huaguang.testandroid.data.repositories.CategoryRepository
import com.huaguang.testandroid.data.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassificationViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val categoryRepository: CategoryRepository,
): ViewModel() {
    val keywords = mutableStateListOf<Keyword>() // TODO: 有点问题，不要放在这里
    val tags = mutableStateListOf<Tag>() // TODO: 有点问题，不要放在这里
    val rootCategories = mutableStateListOf<Category>()
    /**
     * 按 parentId 分组的子类属列表。
     *
     * 除去了根类属，也即 parentId 为 null 的情况。
     */
    private val subCategoriesMap = mutableStateMapOf<Long, List<Category>>()
    val showDialog = mutableStateOf(false)
    /**
     * 缓存父类属的 id，不为 null。
     *
     * 在某个类属下新增子类属时需要。
     * 全局只维护一个实例，每次新增类属时替换。
     */
    private var parentId: Long = 0

    init {
        viewModelScope.launch {
            // 在类属展示块（CategorySection）统一获取根类属和所有的子类属，避免在每个类属项（CategoryItem）中都去获取，
            // 进而导致可能存在的无限循环问题！
            fetchAllCategories()
        }
    }

    /**
     * 新增图标点击。
     *
     * 显示新增对话框，并更新父类属 id 的缓存。
     */
    fun onAddIconClick(parentId: Long) {
        showDialog.value = true
        this.parentId = parentId
    }

    fun fetchTags() {
        TODO("Not yet implemented")
    }

    fun deleteTag(tag: Tag) {
        TODO("Not yet implemented")
    }

    fun deleteKeyword(keyword: Keyword) {
        TODO("Not yet implemented")
    }

    fun fetchKeywords() {
        TODO("Not yet implemented")
    }

    /**
     * 从缓存的 [subCategoriesMap] 中获取指定类属（id）的子类属列表。
     *
     * @param parentId 父类属的 id，不包括根类属。
     */
    fun getSubCategories(parentId: Long): List<Category> {
        return subCategoriesMap[parentId] ?: emptyList()
    }

    /**
     * 新增类属的实质方法。
     *
     * 对话框点击确定后调用。
     */
    fun addCategory(name: String) {
        viewModelScope.launch {
            // 1. 构建新的类属
            val category = Category(name = name, parentId = parentId)
            // 2. 插入数据库
            categoryRepository.insertCategory(category)
        }
    }

    /**
     * 从类属仓库获取所有类属，并更新 [rootCategories] 和 [subCategoriesMap]。
     */
    private suspend fun fetchAllCategories() {
        categoryRepository.getAllCategoriesFlow()
            .collect { categories ->
                // partition：根据什么条件把列表分成两部分
                val (roots, subs) = categories.partition { it.parentId == null }
                rootCategories.clear()
                rootCategories.addAll(roots)

                val groupedByParent = subs.groupBy { it.parentId!! }
                subCategoriesMap.clear()
                subCategoriesMap.putAll(groupedByParent)
            }
    }

}