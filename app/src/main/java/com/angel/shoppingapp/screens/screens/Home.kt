package com.angel.shoppingapp.screens.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.angel.shoppingapp.navigation.Screen
import com.angel.shoppingapp.widgets.ItemCard
import com.angel.shoppingapp.widgets.TopBar

val listofItems = listOf("25.00", "15.99", "22.99", "10.00", "5.00", "8.00")

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopBar(title = "Home", navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Menu,
                        contentDescription = "menu button",
                        tint = Color.White)
                }
            })
        }, content = {
            Surface(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
            }
            Spacer(modifier = Modifier.height(2.dp))
            Column(navController)
        })
}

@Composable
fun Column(navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        items(items = listofItems, itemContent = { item ->
            Spacer(modifier = Modifier.height(14.dp))
                ItemCard(item = item, navController)
        })
    }
}
