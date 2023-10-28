package com.huaguang.testandroid.classifier

object ClassifierFactory {
    fun createClassifier(type: ClassifierType): ClassifierStrategy {
        return when (type) {
            ClassifierType.TYPE1 -> Classifier1()
            ClassifierType.TYPE2 -> Classifier2()
//            ClassifierType.TYPE3 -> Classifier3()
        }
    }
}

enum class ClassifierType {
    TYPE1, TYPE2,
//    TYPE3
}