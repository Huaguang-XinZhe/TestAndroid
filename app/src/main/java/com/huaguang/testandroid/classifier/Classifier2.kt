package com.huaguang.testandroid.classifier

class Classifier2 : ClassifierStrategy {
    private val classifier = initializeClassifier2()
    override fun classify(name: String): String? = classifier.classify(name)

    private fun initializeClassifier2(): KeywordClassifier {
        val classifier = KeywordClassifier()

        val dev = listOf(
            "当前核心", "开发", "时光流", "伴随", "bug", "列表项", "日期", "内存", "隐藏",
            "插入", "间隙", "按钮", "事项", "事件", "配置", "个人", "数据库", "弹窗", "自定义",
            "Android", "类属", "模块", "饼图", "重构", "崩溃", "建个表", "板块", "网址", "输入框",
            "检测", "点击", "上传", "扩展", "切换", "驼峰", "滑动", "网址",
        )
        val resistanceToInertia = listOf(
            "抗性", "惯性", "泄", "触碰", "拨弄",
        )

        classifier.apply {
            insert(dev, "开发")
            insert("探索", "探索")
            insert(resistanceToInertia, "抗性")
        }

        return classifier
    }
}