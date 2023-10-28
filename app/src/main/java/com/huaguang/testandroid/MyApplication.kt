package com.huaguang.testandroid

import android.app.Application
import com.huaguang.testandroid.classifier.ClassifierFactory
import com.huaguang.testandroid.classifier.ClassifierStrategy
import com.huaguang.testandroid.classifier.ClassifierType
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication @Inject constructor() : Application() {

    val classifier: Map<ClassifierType, ClassifierStrategy> by lazy {
        mapOf(
            ClassifierType.TYPE1 to ClassifierFactory.createClassifier(ClassifierType.TYPE1),
            ClassifierType.TYPE2 to ClassifierFactory.createClassifier(ClassifierType.TYPE2),
//            ClassifierType.TYPE3 to ClassifierFactory.createClassifier(ClassifierType.TYPE3)
        )
    }

    // 放到其他类当中
    fun classify(name: String, type: ClassifierType): String? {
        return classifier[type]?.classify(name)
    }


}