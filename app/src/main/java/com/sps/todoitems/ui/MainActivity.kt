package com.sps.todoitems.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
    val todoItems = viewModel.items.collectAsState()
    val loader = viewModel.loading.collectAsState()
    NavHost(
        modifier = modifier.background(color = PurpleGrey80.copy(alpha = 0.2f)),
        navController = navController,
        startDestination = Routes.Main
    ) {
        composable<Routes.Main> {
            TodoHomeScreen(
                todoItems = todoItems.value,
                onAddItemClick = {
                    navController.navigate(Routes.Details)
                }
            )
        }
        composable<Routes.Details> {
            TodoAddItemScreen(
                loading = loader.value,
                modifier = Modifier,
                onAddItem = viewModel::onAddItem
            )
        }
    }
}
