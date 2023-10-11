package com.huaguang.testandroid

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LongPressOutlinedIconButton(
    modifier: Modifier = Modifier,
    iconRes: Int = R.drawable.s, 
    iconSize: Dp = 18.dp,
    onClick: () -> Unit, 
    onLongClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center, 
        modifier = modifier
            .size(iconSize)
            .clip(CircleShape)
            .border(0.6.dp, Color.Black, CircleShape)
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false),
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        Icon(
            painter = painterResource(id = iconRes), 
            contentDescription = null,
            modifier = Modifier.padding(3.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LongPressButtonTest() {
    
    LongPressOutlinedIconButton(
        onClick = {},
        onLongClick = {},
        modifier = Modifier.padding(5.dp)
    )
}