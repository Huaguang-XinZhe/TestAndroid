package com.huaguang.testandroid.pages.class_page

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huaguang.testandroid.data.entities.Category
import com.huaguang.testandroid.data.entities.Keyword
import com.huaguang.testandroid.data.repositories.CategoryRepository
import com.huaguang.testandroid.data.repositories.KeywordRepository
import com.huaguang.testandroid.dialog.DeleteDialogState
import com.huaguang.testandroid.dialog.InputDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassificationViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val keywordRepository: KeywordRepository,
): ViewModel() {
    val rootCategories = mutableStateListOf<Category>()

    /**
     * 按 parentId 分组的子类属列表。
     *
     * 除去了根类属，也即 parentId 为 null 的情况。
     */
    private val subCategoriesMap = mutableStateMapOf<Long, List<Category>>()

    /**
     * 至少有一个关键词的类属及其关联的关键词组成的 Map，Flow 类型。
     */
    val categoryToKeywordsMapFlow = categoryRepository.getCategoryToKeywordsMapFlow()

    /**
     * 适用于新增类属、新增关键词、更新关键词的对话框的状态。
     */
    val inputDialogState: MutableState<InputDialogState> = mutableStateOf(InputDialogState.Hidden)
    val deleteDialogState: MutableState<DeleteDialogState> = mutableStateOf(DeleteDialogState.Hidden)


    init {
        viewModelScope.launch {
            // 在类属展示块（CategorySection）统一获取根类属和所有的子类属，避免在每个类属项（CategoryItem）中都去获取，
            // 进而导致可能存在的无限循环问题！
            fetchAllCategories()
        }
    }

    /**
     * 从缓存的 [subCategoriesMap] 中获取指定类属（id）的子类属列表。
     *
     * @param parentId 父类属的 id，不包括根类属。
     */
    fun getSubCategories(parentId: Long): List<Category> {
        return subCategoriesMap[parentId] ?: emptyList()
    }


    fun onKeywordClick(keyword: Keyword) {
        inputDialogState.value = InputDialogState.UpdateKeyword(
            initialValue = keyword.name,
            onConfirm = { newName ->
                viewModelScope.launch {
                    keywordRepository.updateKeywordName(keyword.id, newName)
                }
            }
        )
    }

    fun onAddKeywordClick(categoryId: Long) {
        inputDialogState.value = InputDialogState.AddKeyword(
            onConfirm = { keyword ->
                viewModelScope.launch {
                    // 对输入进行处理，得到关键词列表
                    val keywords = keyword.trim().split("，", ",", "\n")
                    keywordRepository.insertKeywords(keywords, categoryId)
                }
            }
        )
    }

    fun onAddCategoryClick(parentId: Long) {
        inputDialogState.value = InputDialogState.AddCategory(
            onConfirm = { name ->
                viewModelScope.launch {
                    // 1. 构建新的类属
                    val category = Category(name = name, parentId = parentId)
                    // 2. 插入数据库
                    // TODO: 允许批量插入
                    categoryRepository.insertCategory(category)
                }
            }
        )
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

    fun onKeywordLongClick(keyword: Keyword) {
        deleteDialogState.value = DeleteDialogState.DeleteKeyword(
            onConfirm = {
                viewModelScope.launch {
                    keywordRepository.deleteKeyword(keyword)
                }
            }
        )
    }

}