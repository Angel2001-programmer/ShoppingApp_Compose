package com.angel.shoppingapp.screens.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.angel.shoppingapp.model.Category
import com.angel.shoppingapp.model.getMenu
import com.angel.shoppingapp.model.productsItem
import com.angel.shoppingapp.viewmodels.HomeModel
import com.angel.shoppingapp.widgets.ItemCard
import com.angel.shoppingapp.widgets.SideMenuDrawer
import com.angel.shoppingapp.widgets.TopBar

val list: List<com.angel.shoppingapp.model.Menu> = getMenu()

@Composable
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val homeModel = viewModel<HomeModel>()
    var isExpanded = remember { mutableStateOf(false) }

    homeModel.getCategories()
    Log.d("HomeScreen", "HomeScreen: ${homeModel.categoryListResponse}")
    homeModel.getData()
    Log.d("HomeScreen", "HomeScreen: ${homeModel.productListResponse}")

    Scaffold(
        topBar = { TopBar(drawerState, scope, "Home") }
    ) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(), color = Color(0xFFD5B79C)) {

            SideMenuDrawer(
                drawerState,
                navController
            ) {
                Column(verticalArrangement = Arrangement.Center) {
                    Spacer(modifier = Modifier.height(16.dp))
                    DropDown(homeModel.categoryListResponse, homeModel, isExpanded)

                    if (!isExpanded.value) {
                        MutableColumn(navController = navController, homeModel.chosenListResponse)
                    }
                    MutableColumn(navController = navController, homeModel.productListResponse)
                }
            }
        }
    }
}

@Composable
fun MutableColumn(navController: NavController, list: List<productsItem>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        items(list) { item ->
            Spacer(modifier = Modifier.height(14.dp))
            ItemCard(item = item, navController = navController)
        }
    }
}


@Composable
fun DropDown(data: List<Category>, homeModel: HomeModel, isExpanded: MutableState<Boolean>) {
    var title by remember { mutableStateOf("Category") }

    Card(modifier = Modifier
        .padding(
            start = 8.dp,
            end = 8.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(6.dp)
    ) {
        TextButton(onClick = { isExpanded.value = !isExpanded.value }) {
            Surface(modifier = Modifier.width(260.dp)) {
                Column {
                    Row {
                        Text(title, textAlign = TextAlign.Start)
                        Icon(imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "DropMenu")
                    }
                    if (isExpanded.value) {
                        //visible
                        Box(contentAlignment = Alignment.TopCenter) {
                            Spacer(modifier = Modifier.height(10.dp))
                            LazyColumn(content = {
                                items(items = data) { item ->
                                    TextButton(onClick = {
                                        when (item.name) {
                                            "Clothes" -> {
                                                title = "clothes"
                                                isExpanded.value = false
                                                homeModel.getChosenItems(1)
                                            }
                                            "Electronics" -> {
                                                title = "Electronics"
                                                isExpanded.value = false
                                                homeModel.getChosenItems(2)
                                            }
                                            "Furniture" -> {
                                                title = "Furniture"
                                                isExpanded.value = false
                                                homeModel.getChosenItems(3)
                                            }
                                            "Shoes" -> {
                                                title = "Shoes"
                                                isExpanded.value = false
                                                homeModel.getChosenItems(4)
                                            }
                                            "Others" -> {
                                                title = "Others"
                                                isExpanded.value = false
                                                homeModel.getChosenItems(5)
                                            }
                                            "Manualidades" -> {
                                                title = "Manualidades"
                                                isExpanded.value = false
                                                homeModel.getChosenItems(6)
                                            }
                                        }
                                    }) {
                                        Text(text = item.name, color = Color.Black)
                                    }
                                    Divider(modifier = Modifier.height(2.dp))
                                }
                            })
                        }
                    } else {
                        //invisible
                    }
                }
            }
        }
    }
}