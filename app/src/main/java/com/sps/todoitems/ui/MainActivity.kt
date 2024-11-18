package com.sps.todoitems.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sps.todoitems.core.navigation.Routes
import com.sps.todoitems.core.theme.PurpleGrey80
import com.sps.todoitems.core.theme.TodoItemsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: SharedViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoItemsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RootScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                    )
                }
            }
        }
        viewModel.initialize()
    }
}

@Composable
fun RootScreen(
    modifier: Modifier = Modifier,
    viewModel: SharedViewmodel,
    navController: NavHostController = rememberNavController(),
) {
    val savedTodoItems by viewModel.insertedItems.collectAsState()
    val filteredTodoItems by viewModel.filteredItems.collectAsState()
    val uiActionUpdates by viewModel.actionData.collectAsState()
    NavHost(
        modifier = modifier.background(color = PurpleGrey80.copy(alpha = 0.2f)),
        navController = navController,
        startDestination = Routes.Main
    ) {
        composable<Routes.Main> {
            TodoHomeScreen(
                modifier = modifier,
                savedAllItems = savedTodoItems,
                filteredTodoItems = filteredTodoItems,
                actionHandler = viewModel::onHandleAction,
                onAddItemButtonClick = {
                    navController.navigate(Routes.Details)
                }
            )
        }
        composable<Routes.Details> {
            TodoAddItemScreen(
                action = uiActionUpdates,
                modifier = Modifier,
                onBackClick = { navController.popBackStack() },
                actionHandler = viewModel::onHandleAction,
            )
        }
    }
}
