package com.huaguang.testandroid

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabLayoutExample() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Tab 1", "Tab 2", "Tab 3")

    Scaffold(
        topBar = {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                        },
                        text = {
                            Text(
                                text = title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    )
                }
            }
        }
    ) {
        Log.i(TAG, "TabLayoutExample: it = $it")
        Log.d(TAG, "TabLayoutExample: selectedTabIndex = $selectedTabIndex")
        Box(
            modifier = Modifier.padding(top = it.calculateTopPadding())
        ) {
            when (selectedTabIndex) {
                0 -> Text("Content of Tab 1")
                1 -> Text("Content of Tab 2")
                2 -> Text("Content of Tab 3")
                else -> throw IllegalArgumentException("Unexpected index: $selectedTabIndex")
            }
        }
    }
}

/**
 * 通用化基础 TabRow
 */
@Composable
fun MyTabRow(
    modifier: Modifier = Modifier,
    tabTitles: List<String>, 
    selectedTabIndex: MutableState<Int>,
) {
    TabRow(
        selectedTabIndex = selectedTabIndex.value,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.value])
            )
        },
        modifier = modifier
    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex.value == index,
                onClick = {
                    selectedTabIndex.value = index
                },
                text = {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            )
        }
    }
}

// TODO: 这个 Composable 还有问题，标题会被压缩，而且不能滑动。
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyTabRowWithPager(
    modifier: Modifier = Modifier,
    tabTitles: List<String>,
    selectedTabIndex: MutableState<Int>,
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState: PagerState = rememberPagerState(initialPage = 0)

    // 使用协程来同步 PagerState 和 MutableState
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex.value = pagerState.currentPage
    }

    // TabRow
    TabRow(
        selectedTabIndex = selectedTabIndex.value,
        modifier = modifier
    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex.value == index,
                onClick = {
                    selectedTabIndex.value = index
                    coroutineScope.launch {
                        pagerState.scrollToPage(index)
                    }
                }
            ) {
                Text(text = title)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TabLayoutExamplePreview() {
//    MaterialTheme {
//        TabLayoutExample()
//    }
    val selectedTabIndex = remember { mutableStateOf(0) }
    val tabTitles = listOf("Tab 1", "Tab 2", "Tab 3")
    
//    MyTabRow(tabTitles = tabTitles, selectedTabIndex = selectedTabIndex)
    MyTabRowWithPager(
        tabTitles = tabTitles,
        selectedTabIndex = selectedTabIndex
    )
}
