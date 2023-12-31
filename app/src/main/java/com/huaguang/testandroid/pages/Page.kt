package com.huaguang.testandroid.pages

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.huaguang.testandroid.R

sealed class Page(
    val route: String,
    @StringRes val labelRes: Int,
    @DrawableRes val iconRes: Int,
) {
    object Record : Page("record", R.string.record, R.drawable.record)

    object List : Page("list", R.string.list, R.drawable.list)

    object Statistic : Page("statistic", R.string.statistic, R.drawable.statistic)

    object Classification: Page("category", R.string.classification, R.drawable.classification)

}


val tabs = listOf(
    Page.Record,
    Page.List,
    Page.Statistic,
    Page.Classification,
)
