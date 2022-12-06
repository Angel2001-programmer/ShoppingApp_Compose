package com.angel.shoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.angel.shoppingapp.navigation.Navigation

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingAppTheme {
                navController = rememberNavController()
                Navigation(navController = navController)
            }
        }
    }

    @Composable
    fun ShoppingAppTheme(content: @Composable () -> Unit) {
        content()
    }

    @Preview
    @Composable
    fun DefaultPreview() {
        ShoppingAppTheme {
            Navigation(navController = navController)
        }
    }
}

