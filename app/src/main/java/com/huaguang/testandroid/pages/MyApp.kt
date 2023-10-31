package com.huaguang.testandroid.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.huaguang.testandroid.RecordPage
import com.huaguang.testandroid.pages.class_page.ClassificationPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    // 创建 NavController 实例
    val navController = rememberNavController()

    // 定义应用的脚手架结构
    Scaffold(
        bottomBar = {
            BottomNavBar(navController) // 传递 NavController 实例到 BottomNavBar
        }
    ) { innerPadding ->
        // 用于显示页面内容的导航宿主
        NavHost(
            navController = navController,
            startDestination = Page.Classification.route, // 设置启动目的地为 Record 页面
            modifier = Modifier.padding(innerPadding)
        ) {
            // 遍历 tabs 列表中的每个页面配置，并创建相应的导航图
            tabs.forEach { page ->
                composable(route = page.route) {
                    // 根据当前路由显示对应的页面组件
                    when (page) {
                        is Page.Record -> RecordPage(hiltViewModel())
                        is Page.List -> ListPage()
                        is Page.Statistic -> StatisticPage()
                        is Page.Classification -> ClassificationPage(hiltViewModel())
                    }
                }
            }
        }
    }
}


@Composable
fun ListPage() {
    // List 页面的内容
}

@Composable
fun StatisticPage() {
    // Statistic 页面的内容
}
