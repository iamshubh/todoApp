package com.sps.todoitems.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sps.todoitems.R
import com.sps.todoitems.core.theme.Green80
import com.sps.todoitems.core.theme.TodoTypography
import kotlinx.coroutines.delay

@Composable
fun TodoAddItemScreen(
    action: UiAction,
    actionHandler: (UiAction) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier.fillMaxSize()) {
        TodoItemsContent(
            modifier = modifier,
            actionHandler = { if (action !is UiAction.Loading) actionHandler.invoke(it) },
        )
        when (action) {
            UiAction.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            UiAction.SuccessAddition -> {
                Row(
                    modifier = Modifier
                        .background(Green80.copy(alpha = 0.2f))
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.added_successfully),
                        style = TodoTypography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
                LaunchedEffect(Unit) {
                    delay(500)
                    actionHandler.invoke(UiAction.None)
                    onBackClick.invoke()
                }
            }

            UiAction.FailureAddition -> {
                Row(
                    modifier = Modifier
                        .background(Red.copy(alpha = 0.2f))
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.failure_todo),
                        style = TodoTypography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
                LaunchedEffect(Unit) {
                    delay(2000)
                    actionHandler.invoke(UiAction.None)
                }
            }

            else -> {}
        }
    }
}


@Composable
fun TodoItemsContent(
    actionHandler: (UiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
    ) {
        var text by remember { mutableStateOf("") }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                text = it
            },
            label = {
                Text(text = stringResource(R.string.add_todo))
            },
            singleLine = true,
        )
        Spacer(Modifier.height(32.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RectangleShape,
            onClick = {
                if (text.isNotEmpty()) actionHandler.invoke(UiAction.ItemAddition(text))
            }) {
            Text(text = stringResource(R.string.add_todo), style = TodoTypography.bodyMedium)
        }
    }
}

@Preview
@Composable
fun TodoAddItemScreenPreview() {
    TodoAddItemScreen(
        action = UiAction.Loading,
        actionHandler = {},
        onBackClick = {},
    )
}