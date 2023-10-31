package com.huaguang.testandroid.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavHostController) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background,
    ) {
        tabs.forEach { page ->
            val selected = currentRoute(navController) == page.route
            val selectedColor = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            }

            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = page.iconRes),
                        contentDescription = null,
                        tint = selectedColor,
                        modifier = Modifier.padding(bottom = 3.dp) // 往 label 上加 topPadding 居然没有效果，往 Icon 上加还有哩
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = page.labelRes),
                        fontSize = 12.sp,
                        color = selectedColor,
                    )
                },
                selected = selected,
                onClick = {
                    // This is where you handle navigation
                    navController.navigate(page.route) {
                        // Avoid multiple copies of the same destination
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                selectedContentColor = MaterialTheme.colorScheme.primary, // 这个改变的是选中 Tab 水波纹的颜色
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}