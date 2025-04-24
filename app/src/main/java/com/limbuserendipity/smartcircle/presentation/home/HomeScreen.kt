package com.limbuserendipity.smartcircle.presentation.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel

@Composable
fun HomeScreen(
    viewModel : HomeViewModel
){

    val state by viewModel.state.collectAsState()
    SearchContent(state.arduinos.map { it.ip })

    LaunchedEffect(Unit) {
        viewModel.searchOther()
    }

}


@Composable
fun SearchContent(
    ips : List<String>
){

    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .padding(32.dp)
    ) {

        item {
            Text(text = "ips:")
        }

        items(ips){ ip ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
            ){
                Text(
                    text = ip,
                    fontSize = 72.sp
                )
            }
        }
    }

}
