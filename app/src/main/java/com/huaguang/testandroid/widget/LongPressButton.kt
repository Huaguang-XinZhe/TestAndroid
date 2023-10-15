package com.huaguang.testandroid.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.huaguang.testandroid.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LongPressButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    val shape = CircleShape

    Box(
        modifier = modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.primary, shape)
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false),
                onClick = onClick,
                onLongClick = onLongClick,
            ),
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LongPressIconButton(
    modifier: Modifier = Modifier,
    iconRes: Int,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.padding(5.dp)
            .size(40.dp)
            .clip(CircleShape)
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false),
                onClick = onClick,
                onLongClick = onLongClick,
            ),
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
    
}

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
fun LongPressTest() {

//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//
//    ) {
//        DoubleClickButton(
//            text = "新增",
//            onClick = { /*TODO*/ },
//            modifier = Modifier.padding(10.dp)
//        ) {
//
//        }
//
//        Button(onClick = { /*TODO*/ }) {
//            Text(text = "新增")
//        }
//    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ) {
        LongPressIconButton(
            iconRes = R.drawable.stop,
            onClick = { /*TODO*/ },

        ) {

        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.stop),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }

}