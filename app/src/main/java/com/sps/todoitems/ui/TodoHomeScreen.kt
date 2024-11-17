package com.sps.todoitems.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sps.todoitems.R
import com.sps.todoitems.core.theme.PurpleGrey80
import com.sps.todoitems.core.theme.TodoTypography
import com.sps.todoitems.data.TodoApiModel

@Composable
fun TodoHomeScreen(
    viewModel: SharedViewmodel,
    onAddItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val items = viewModel.items.collectAsState()
    Box(
        modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TodoItemsContainer(items.value)
        AddTodoItemButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            onAddItemClick()
        }
    }
}

@Composable
fun AddTodoItemButton(modifier: Modifier = Modifier, actionHandler: () -> Unit) {
    Box(
        modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(color = PurpleGrey80)
            .clickable {
                actionHandler.invoke()
            }) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = android.R.drawable.ic_menu_add),
            contentDescription = null,
        )
    }
}

@Composable
fun TodoItemsContainer(todoItems: List<TodoApiModel>, modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            text = stringResource(R.string.title_todo_items),
            style = TodoTypography.titleLarge.copy(
                textAlign = TextAlign.Center,
            )
        )
        if (todoItems.isEmpty()) {
            Text(
                text = stringResource(R.string.no_items_text),
                style = TodoTypography.bodyLarge
            )
        } else {
            TodoContent(todoItems = todoItems)
        }
    }
}

@Composable
fun TodoContent(
    todoItems: List<TodoApiModel>,
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
                Text(text = todoItems[it].text)
            }
        }
    }
}

@Preview
@Composable
fun Previews(modifier: Modifier = Modifier) {
    Column {
        TodoItemsContainer((1..9).map {
            TodoApiModel(
                text = "item $it",
                timeStamp = System.currentTimeMillis()
            )
        })
        AddTodoItemButton {
            println("add clicked")
        }
    }
}