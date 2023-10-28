package com.huaguang.testandroid.classifier

class Classifier1 : ClassifierStrategy {
    private val classifier = initializeClassifier()
    override fun classify(name: String): String? = classifier.classify(name)

    private fun initializeClassifier(): KeywordClassifier {
        val classifier = KeywordClassifier()

        val routine = listOf("煮吃", "买吃", "洗澡", "洗漱", "厕", "外吃", "热吃", "吃晚饭", "买菜", "晾衣", "拿快递", "理发")
        val family = listOf("老妈", "爷爷")
        val rest = listOf("睡", "躺歇", "眯躺", "远观")
        val exercise = listOf("慢跑", "俯卧撑")
        val communication = listOf("聊天", "通话", "鸿", "铮", "师父", "小洁", "小聚",)
        val frame = listOf("财务", "收集库", "时间统计", "清整", "规划", "省思", "统析", "仓库", "预算",
            "纸上统计", "思考", "草思")
        val frontEnd = listOf("HTML", "CSS", "JavaScript", "前端", "Vue", "React")
        val underlyingTech = listOf("计算机底层", "图解网络")
        val fallow = listOf("散步", "打字", "抽烟", "吹风", "休闲")
        val breach = listOf("躺刷", "贪刷", "朋友圈", "刷视频", "QQ空间", "Q朋抖",
            "刷手机", "躺思", "刷抖音", "动态", "贪观")
        val masturbation = listOf("xxx", "泄", "淫",)
        val entertainment = listOf("续观", "看电影")
        val explore = listOf("探索", "逛鱼皮星球", )
        val spring = listOf("spring", "SV", "Spring", "sv")

        classifier.apply {
            insert(routine, "常务")
            insert(family, "家人")
            insert(rest, "休息")
            insert(exercise, "锻炼")
            insert(communication, "交际")
            insert(frame, "框架")
            insert(frontEnd, "前端")
            insert(underlyingTech, "底层")
            insert(spring, "Spring")
            insert("时光流", "时光流")
            insert(explore, "探索")
            insert(fallow, "休闲")
            insert(breach, "违破")
            insert(masturbation, "xxx")
            insert(entertainment, "娱乐")
        }

        return classifier
    }
}




