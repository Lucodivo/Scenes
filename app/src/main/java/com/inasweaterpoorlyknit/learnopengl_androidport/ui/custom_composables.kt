package com.inasweaterpoorlyknit.learnopengl_androidport.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val listItemFontSize = 20.sp
val listPadding = 8.dp
val halfListPadding = listPadding / 2
val listItemTextPadding = 8.dp

val ScenesIcons = Icons.Rounded

@Composable
fun ScenesListItem(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Surface(
        elevation = 1.dp,
        shape = MaterialTheme.shapes.large,
        content = content,
        modifier = modifier
            .padding(horizontal = listPadding, vertical = halfListPadding)
            .fillMaxWidth()
    )
}

@Composable
fun ListItemText(text: String, color: Color = Color.Unspecified, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = listItemFontSize,
        textAlign = TextAlign.Center,
        color = color,
        modifier = modifier
            .wrapContentHeight(Alignment.CenterVertically) // center vertically
            .padding(all = listItemTextPadding)
    )
}

@Composable
fun ListItemTextWithLeftIcon(text: String, textColor: Color = Color.Unspecified, icon: ImageVector, modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.Center, modifier = modifier) {
        Icon(icon,
            contentDescription = icon.name,
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically))
        ListItemText(text, color = textColor)
    }
}

@Composable
fun ListItemTextWithRightIcon(text: String, textColor: Color = Color.Unspecified, icon: ImageVector, modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.Center, modifier = modifier) {
        ListItemText(text, color = textColor)
        Icon(icon,
            contentDescription = icon.name,
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically))
    }
}

@Composable
fun ListItemSwitch(text: String, defaultState: Boolean, onClick: (Boolean) -> Unit) {
    val checkedState = remember { mutableStateOf(defaultState) }
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.clickable {
            val newState = !checkedState.value
            checkedState.value = newState
            onClick(newState)
        }) {
        Text(
            text = text,
            fontSize = listItemFontSize,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .padding(start = 10.dp, end = listItemTextPadding)
        )
        Switch(checked = checkedState.value,
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .padding(horizontal = listItemTextPadding),
            onCheckedChange = {
                val newState = !checkedState.value
                checkedState.value = newState
                onClick(newState)
            })
    }
}