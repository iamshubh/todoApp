package com.sps.todoitems.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp


@Composable
fun TodoHomeScreen(modifier: Modifier = Modifier) {
    TodoContent(listOf(), modifier)
}

@Composable
fun TodoContent(
    todoItems: List<String>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        items(todoItems.size) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .border(1.dp, color = Color.Black, shape = RectangleShape)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = todoItems[it])
            }
        }
    }
}