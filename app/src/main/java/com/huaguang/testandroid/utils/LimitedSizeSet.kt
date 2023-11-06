package com.huaguang.testandroid.utils

/**
 * 自定义有限集合，通过删除前边的元素维持集合大小。
 *
 * 继承自 LinkedHashSet，以保证有序性。
 */
class LimitedSizeSet<T>(private val maxSize: Int) : LinkedHashSet<T>() {
    override fun add(element: T): Boolean {
        if (size >= maxSize) {
            // 删除最早的元素
            remove(first())
        }
        return super.add(element)
    }

    /**
     * 替换第一个元素。
     *
     * 先把元素转换为可变 List，然后替换，最后再换回来。
     */
    fun replaceFirst(newElement: T) {
        // 转换为 MutableList 并替换第一个元素
        val mutableList = this.toMutableList()
        mutableList[0] = newElement

        // 清空原有的 LinkedHashSet 并添加更新后的元素
        this.clear()
        this.addAll(mutableList)
    }

}


