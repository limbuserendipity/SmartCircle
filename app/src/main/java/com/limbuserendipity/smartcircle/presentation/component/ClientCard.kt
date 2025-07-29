package com.limbuserendipity.smartcircle.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ClientCard(
    number: String,
    id: String,
    isConnect: Boolean,
    onLightClick: () -> Unit,
    onDetailsClick: () -> Unit
) {

    ClientCardContent(
        number = number,
        id = id,
        isConnect = isConnect,
        onLightClick = onLightClick,
        onDetailsClick = onDetailsClick
    )
}

@Composable
fun ClientCardContent(
    number: String,
    id: String,
    isConnect : Boolean,
    onLightClick: () -> Unit,
    onDetailsClick: () -> Unit
){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(16.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .padding(8.dp)
    ){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset(x = (-28).dp, y = (-28).dp)
                .clickable {
                    onLightClick()
                }
        ){
            Circle(
                isConnect = isConnect
            ) {
                Text(
                    text = number,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        }

        8.dp.Space()

        Text(
            text = id
        )

        Space()

        Box(
            modifier = Modifier.clickable {
                onDetailsClick()
            }
        ){
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = ""
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

@Preview()
@Composable
fun PreviewClientCard(){
    ClientCard(
        number = "2",
        id = "ESP32-460B65",
        isConnect = true,
        { }
    ) { }
}