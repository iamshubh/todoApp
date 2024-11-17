package com.sps.todoitems.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.sps.todoitems.core.theme.TodoTypography

@Composable
fun TodoAddItem(modifier: Modifier = Modifier, onAddItem: (String) -> Unit) {
    Row(
        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        var text by remember { mutableStateOf("") }
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = {
                Text("add Todo Item")
            }
        )
        Spacer(
            Modifier
                .width(4.dp)
                .weight(1f)
        )
        Button(
            shape = RectangleShape,
            onClick = {
                if (text.isNotEmpty()) onAddItem(text)
            }) {
            Text(text = "Add", style = TodoTypography.bodyMedium)
        }
    }
}