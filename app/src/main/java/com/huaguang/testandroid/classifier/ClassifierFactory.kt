package com.huaguang.testandroid.classifier

import com.huaguang.testandroid.data.repositories.KeywordRepository

class ClassifierFactory(private val keywordRepository: KeywordRepository) {

    suspend fun createKeywordClassifier(): KeywordClassifier {
        val classifier = KeywordClassifier()
        val keywordWithCategories = keywordRepository.getAllKeywordsWithCategories()

        keywordWithCategories.forEach {
            classifier.insert(it.keyword, it.category)
        }

        return classifier
    }
}
