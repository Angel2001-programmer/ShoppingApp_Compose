package com.angel.shoppingapp.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.angel.shoppingapp.widgets.ItemCard
import com.angel.shoppingapp.widgets.TopBar

val listofItems = listOf("25.00", "15.99", "22.99", "10.00", "5.00", "8.00")

    @Preview
    @Composable
    fun HomeScreen(){
        Scaffold(
            topBar = { TopBar(title = "Home")
            }, content = {Surface(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
                }
                Spacer(modifier = Modifier.height(2.dp))
                Column()
            })
    }

    @Composable
    fun Column(){
        LazyColumn(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
            items(items = listofItems, itemContent = {item ->
                Spacer(modifier = Modifier.height(14.dp))
                ItemCard(item)
            })
        }
    }
