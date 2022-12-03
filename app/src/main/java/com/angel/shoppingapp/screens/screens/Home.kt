package com.angel.shoppingapp.screens.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.angel.shoppingapp.model.getMenu
import com.angel.shoppingapp.navigation.Screen
import com.angel.shoppingapp.widgets.ItemCard
import com.angel.shoppingapp.widgets.SideMenuDrawer
import com.angel.shoppingapp.widgets.TopBar
import kotlinx.coroutines.launch
import okhttp3.Interceptor.Companion.invoke

val listofItems = listOf("25.00", "15.99", "22.99", "10.00", "5.00", "8.00")
val list: List<com.angel.shoppingapp.model.Menu> = getMenu()

@Composable
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBar(title = "Home", navController, navigationIcon = {
                IconButton(onClick = { if (drawerState.isClosed){
                    scope.launch {
                        Log.d("TopBar", "TopBar: tapped")
                        drawerState.open()
                    }
                } else if (drawerState.isOpen) {
                    scope.launch {
                        Log.d("TopBar", "TopBar: closed")
                        drawerState.close()
                    }
                }

                }) {
                    Icon(Icons.Default.Menu,
                        contentDescription = "menu button",
                        tint = Color.White)
                }})
        }, content = {
            Surface(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
            }
            ModalDrawer(
                drawerState = drawerState,
                drawerContent = {
                    LazyColumn {
                        items(items = list, itemContent = { item ->
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp), verticalAlignment = Alignment.CenterVertically) {
                                Button(
                                    shape = RectangleShape,
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                                    onClick = { when(item.title){
                                        "Home" -> {
                                            navController.navigate(Screen.HomeScreen.route)
                                        }
                                        "Basket" -> {
                                            navController.navigate(Screen.BasketScreen.route)
                                        }
                                        else ->
                                            Log.d("Navigation", "SideMenuDrawer: Couldn't find a valid navigation route.")
                                    } }
                                ) {

                                    Icon(imageVector = item.icon,
                                        contentDescription = "Home Icon",
                                        modifier = Modifier.padding(end = 5.dp))
                                    Text(text = item.title,
                                        modifier = Modifier.weight(1f))
                                }
                            }
                        })
                    }
                },
                content = {}
            )

            Spacer(modifier = Modifier.height(2.dp))
//            Column(navController)
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
