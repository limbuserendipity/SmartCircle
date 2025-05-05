package com.limbuserendipity.smartcircle.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ClientCard(
    number: String,
    ip: String,
    time: String,
    massage : String,
    isConnect : Boolean,
    onLightClick : () -> Unit
) {

    Column(
        modifier = Modifier
            .width(250.dp)
            .padding(16.dp)
    ) {
        ClientCardContent(
            number = number,
            ip = ip,
            time = time,
            massage = massage,
            isConnect = isConnect
        )
        ClientCardActionRow(
            onLightClick = onLightClick
        )
    }

}

@Composable
fun ClientCardContent(
    number: String,
    ip: String,
    time: String,
    massage : String,
    isConnect : Boolean,
){
    Box(
        modifier = Modifier
            .width(250.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            )
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Box {
                Circle(
                    isConnect = isConnect
                ) {
                    Text(
                        text = number,
                        fontWeight = FontWeight.Bold,
                        fontSize = 34.sp
                    )
                }
            }
            32.dp.Space()
            Text(
                text = ip,
                fontSize = 18.sp
            )

            Text(
                text = time,
                fontSize = 18.sp
            )

            Text(
                text = massage,
                fontSize = 18.sp
            )

        }
    }
}

@Composable
fun ClientCardActionRow(
    onLightClick : () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
            )
    ){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clickable(onClick = onLightClick)
        ){
            Icon(
                imageVector = Icons.Default.Star,
                tint = Color.White,
                contentDescription = "light"
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clickable(onClick = onLightClick)
        ){
            Icon(
                imageVector = Icons.Default.LocationOn,
                tint = Color.White,
                contentDescription = "time"
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clickable(onClick = onLightClick)
        ){
            Icon(
                imageVector = Icons.Default.Home,
                tint = Color.White,
                contentDescription = "home"
            )
        }


    }
}

@Composable
fun Dp.Space() {
    Spacer(modifier = Modifier.size(this))
}

@Composable
fun RowScope.Space() {
    Spacer(modifier = Modifier.weight(1f))
}
