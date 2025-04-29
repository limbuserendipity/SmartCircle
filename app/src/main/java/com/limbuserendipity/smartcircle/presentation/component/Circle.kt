package com.limbuserendipity.smartcircle.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.unit.dp

@Composable
fun Circle(
    content: @Composable () -> Unit
) {

    val colors = listOf(
        Color.White,
        Color(0xFF82ecec),
        Color(0xFFfe6dc8),
        Color.White,
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(160.dp)
            .border(
                width = 24.dp,
                brush = Brush.linearGradient(
                    colors
                ),
                shape = CircleShape
            )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(130.dp)
                .background(Color.White)
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(colors.reversed()),
                    shape = CircleShape
                )
        ){
            content()
        }
    }

}