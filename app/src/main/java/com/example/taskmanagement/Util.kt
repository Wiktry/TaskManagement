package com.example.taskmanagement

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun IconHolder(
    painter: Painter,
    contentDesc: String,
    height: Int = 30,
) {
    Box(modifier = Modifier.height(height.dp)) {
        Image(
            painter = painter,
            contentDescription = contentDesc,
            contentScale = ContentScale.Fit
        )
    }
}

fun createTask(title: String, desc: String, onClick: () -> Unit) {
    if (title.isNotEmpty() && desc.isNotEmpty()) {
        onClick()
    }
}