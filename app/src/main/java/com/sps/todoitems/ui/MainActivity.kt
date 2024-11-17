package com.sps.todoitems.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sps.todoitems.core.navigation.Routes
import com.sps.todoitems.core.theme.TodoItemsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoItemsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RootScreen(
                        modifier = Modifier.padding(innerPadding),
                        navController = rememberNavController()
                    )
                }
            }
        }
    }
}

@Composable
fun RootScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Routes.Main
    ) {
        composable<Routes.Main> {
            Box(Modifier)
        }
        composable<Routes.Details> {
            Box(Modifier)
        }
    }
}
