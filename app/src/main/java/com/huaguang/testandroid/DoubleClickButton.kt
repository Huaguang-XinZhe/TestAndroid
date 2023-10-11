package com.huaguang.testandroid

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DoubleClickButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    onDoubleClick: () -> Unit,
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
                onDoubleClick = onDoubleClick,
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
fun DoubleClickIconButton(
    modifier: Modifier = Modifier,
    iconRes: Int,
    onClick: () -> Unit,
    onDoubleClick: () -> Unit,
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
                onDoubleClick = onDoubleClick,
            ),
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
    
}

@Preview(showBackground = true)
@Composable
fun DoubleClickButtonTest() {

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
        DoubleClickIconButton(
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