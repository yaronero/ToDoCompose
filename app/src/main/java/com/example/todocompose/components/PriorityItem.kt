package com.example.todocompose.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todocompose.data.models.Priority
import com.example.todocompose.ui.theme.Typography
import com.example.todocompose.util.LARGE_PADDING
import com.example.todocompose.util.PRIORITY_INDICATOR_SIZE

@Composable
fun PriorityItem(priority: Priority) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        PriorityIndicatorCircle(priority)
        Text(
            text = priority.name,
            style = Typography.subtitle2,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(start = LARGE_PADDING)
        )
    }
}

@Composable
fun PriorityIndicatorCircle(priority: Priority, modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier.size(PRIORITY_INDICATOR_SIZE)
    ) {
        drawCircle(color = priority.color)
    }
}

@Composable
@Preview
private fun PriorityItemPreview() {
    PriorityItem(priority = Priority.HIGH)
}