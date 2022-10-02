package com.angel.shoppingapp.screens.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.angel.shoppingapp.model.Product
import com.angel.shoppingapp.widgets.RowCard

var list = listOf<Product>()

    @Preview
    @Composable
    fun homePage(){
        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            list()
        }
    }

    @Composable
    fun list(){
            LazyColumn(modifier = Modifier.padding(vertical = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                items(items = list, itemContent = { item ->
                    Log.d("ListItems", "list: $item")
                    RowCard(item)
                    Spacer(modifier = Modifier.height(8.dp))
                })
            }
    }