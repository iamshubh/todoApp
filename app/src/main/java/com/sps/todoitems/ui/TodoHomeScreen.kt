package com.sps.todoitems.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sps.todoitems.R
import com.sps.todoitems.core.theme.PurpleGrey80
import com.sps.todoitems.core.theme.TodoTypography
import com.sps.todoitems.data.TodoApiModel

@Composable
fun TodoHomeScreen(
    savedAllItems: List<TodoApiModel>,
    filteredTodoItems: List<TodoApiModel>,
    onAddItemButtonClick: () -> Unit,
    actionHandler: (UiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TodoItemsContainer(
            filteredTodoItems = filteredTodoItems,
            savedAllItems = savedAllItems,
            actionHandler = actionHandler
        )
        AddTodoItemButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            onAddItemButtonClick()
        }
    }
}

@Composable
fun AddTodoItemButton(modifier: Modifier = Modifier, onAddItemButtonClick: () -> Unit) {
    Box(
        modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(color = PurpleGrey80)
            .clickable {
                onAddItemButtonClick()
            }) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = android.R.drawable.ic_menu_add),
            contentDescription = null,
        )
    }
}

@Composable
fun TodoItemsContainer(
    savedAllItems: List<TodoApiModel>,
    filteredTodoItems: List<TodoApiModel>,
    actionHandler: (UiAction) -> Unit,
    modifier: Modifier = Modifier
) {
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
        if (savedAllItems.isEmpty()) {
            Text(
                text = stringResource(R.string.no_items_text),
                style = TodoTypography.bodyLarge
            )
        } else {
            var searchText by remember { mutableStateOf("") }
            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                maxLines = 1,
                label = { Text(text = stringResource(R.string.search_todos)) },
                value = searchText,
                onValueChange = {
                    searchText = it
                    actionHandler.invoke(UiAction.ItemSearch(it))
                }
            )
            TodoContent(
                todoItems = filteredTodoItems,
                actionHandler = actionHandler
            )
        }
    }
}

@Composable
fun TodoContent(
    todoItems: List<TodoApiModel>,
    actionHandler: (UiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(todoItems.size) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .weight(1f)
                        .border(1.dp, color = Color.Black, shape = RectangleShape)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = todoItems[it].text)
                }
                Spacer(Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .clickable {
                            actionHandler.invoke(UiAction.ItemDeletion(todoItems[it].id))
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize(),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                        contentDescription = null,
                    )
                }
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
        }, (1..9).map {
            TodoApiModel(
                text = "item $it",
                timeStamp = System.currentTimeMillis()
            )
        }, {})
        AddTodoItemButton {
            println("add clicked")
        }
    }
}