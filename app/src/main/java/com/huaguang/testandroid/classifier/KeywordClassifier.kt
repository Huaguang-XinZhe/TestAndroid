package com.huaguang.testandroid.classifier

class TrieNode {
    var isEndOfWord: Boolean = false
    val children = mutableMapOf<Char, TrieNode>()
    var category: String? = null
}

class KeywordClassifier {
    private val root = TrieNode()

    /**
     * 批量插入关键词。
     *
     * 其实内部调用了单个插入的方法。
     */
    fun insert(keywords: List<String>, category: String) {
        keywords.forEach { keyword ->
            insert(keyword, category)
        }
    }

    fun classify(name: String): String? {
        // 通过嵌套循环的方式遍历所有可能的子串，从前往后，由长到短，找到第一个匹配的关键词就返回
        for (i in name.indices) {
            var node = root
            for (j in i until name.length) {
                node = node.children[name[j]] ?: break
                if (node.isEndOfWord) {
                    return node.category
                }
            }
        }
        return null // 没有找到类属，就返回 null
    }

    /**
     * 单个插入的方法。
     */
    fun insert(keyword: String, category: String) {
        var node = root
        for (ch in keyword) {
            node = node.children.computeIfAbsent(ch) { TrieNode() }
        }
        node.isEndOfWord = true
        node.category = category
    }
}