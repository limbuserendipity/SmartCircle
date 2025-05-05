package com.limbuserendipity.smartcircle.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Circle(
    isConnect: Boolean,
    content: @Composable () -> Unit
) {

    val colors = if (isConnect)
        listOf(
            Color(0xFF82ecec),
            Color(0xFFfe6dc8),
        )
    else listOf(
        Color.Gray,
        Color.LightGray,
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(160.dp)
            .drawBehind {
                circle(colors)
            }
    ) {
        content()
    }
}

fun DrawScope.circle(
    colors: List<Color>,
) {

    val gradient = Brush.verticalGradient(
        colors = colors
    )

    val brush = Brush.radialGradient(
        0.6f to Color.White, 1.0f to Color.Transparent,
    )

    drawCircle(
        brush = gradient
    )

    drawCircle(
        brush = brush
    )

}

@Preview
@Composable
fun PreviewCircle() {
    Circle(false) {
        Text(
            text = "4",
            fontSize = 64.sp
        )
    }
}