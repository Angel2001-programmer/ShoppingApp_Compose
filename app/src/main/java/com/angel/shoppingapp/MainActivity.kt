package com.angel.shoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.angel.shoppingapp.screens.home.HomeScreen
import com.angel.shoppingapp.screens.home.LoginScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingAppTheme {
                LoginScreen()
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
            HomeScreen()
        }
    }
}

