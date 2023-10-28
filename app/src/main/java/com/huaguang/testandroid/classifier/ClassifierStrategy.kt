package com.huaguang.testandroid.classifier

interface ClassifierStrategy {
    fun classify(name: String): String?
}
