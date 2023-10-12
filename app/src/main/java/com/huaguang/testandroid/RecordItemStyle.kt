package com.huaguang.testandroid

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huaguang.testandroid.ui.theme.DeepGreen
import com.huaguang.testandroid.ui.theme.Primary
import com.huaguang.testandroid.ui.theme.PurpleWhite

// TODO: 这里边有些颜色写死了，能不能应用主题？
sealed class RecordItemStyle(
    val iconSize: Dp = 15.dp,
    val iconTopPadding: Dp = 1.dp,
    val iconColor: Color = Primary,
    val nameSize: TextUnit = 17.sp,
    @DrawableRes val iconRes: Int = R.drawable.add,
    val isTag: Boolean = true,
    val backgroundColor: Color = PurpleWhite,
) {
    object Main : RecordItemStyle(
        iconSize = 18.dp,
        nameSize = 20.sp,
        iconRes = R.drawable.main,
        isTag = false,
        iconColor = DeepGreen,
        backgroundColor = Color.Green.copy(alpha = 0.03f)
    )

    object Add : RecordItemStyle()

    object Insert : RecordItemStyle(
        iconTopPadding = 0.dp,
        iconRes = R.drawable.insert,
    )
}
