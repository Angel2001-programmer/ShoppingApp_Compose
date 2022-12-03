package com.angel.shoppingapp.widgets

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.angel.shoppingapp.model.Product
import com.angel.shoppingapp.model.getMenu
import com.angel.shoppingapp.model.getProduct
import com.angel.shoppingapp.navigation.Screen
import com.angel.shoppingapp.screens.screens.list
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ItemCard(item: String, navController: NavController) {
    Card(modifier = Modifier
        .clip(shape = RoundedCornerShape(3, 3, 3, 3))
        .clickable
        {
            navController.navigate(
                Screen.Details.route)
            Log.d("Item Card", "Column: $item")
        }) {
        Column {
            AsyncImage(
                modifier = Modifier.height(160.dp),
                model = "https://www.foodbev.com/wp-content/gallery/food-releases-march-2019/Hazelnut-Spread-M-and-Ms.jpg",
                contentDescription = ""
            )

            Card(shape = RoundedCornerShape(0, 0, 10, 10), elevation = 12.dp) {
                Row(
                    modifier = Modifier
                        .height(70.dp)
                        .width(255.dp)
                        .background(Color(0xFFBFBDBD)),
                )

                {
                    Text(
                        text = "Price £$item",
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .background(Color(0xFFBFBDBD))
                            .weight(1F),
                        style = MaterialTheme.typography.h5,
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1F),
                        border = BorderStroke(1.5.dp, Color.Black),
                        shape = RoundedCornerShape(10),
                        backgroundColor = Color(0xFFD9D9D9), elevation = 12.dp) {

                        Text(
                            text = "Average rating\n5 stars",
                            style = MaterialTheme.typography.subtitle1,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BasketCard(list: List<Product> = getProduct()) {
    var total = 0.00

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(items = list, itemContent = { item ->

            Row(modifier = Modifier.padding(8.dp)) {
                AsyncImage(model = item.thumbnail, contentDescription = "product image")
                Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                    Text(text = item.title)
                    Text(text = "£${item.price}")
                }
            }
        })
    }

    Box(contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
        com.angel.shoppingapp.components.Button(onClick = { /*TODO*/ },
            elevation = ButtonDefaults.elevation(2.dp),
            value = "Total £$total")
    }
}

@Composable
fun MoreDetailsColumn(list: List<Product> = getProduct()) {
    Box(modifier = Modifier
        .padding(start = 8.dp)) {
        Column {
            com.angel.shoppingapp.components.Text(
                value = "Title",
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.h4
            )
            com.angel.shoppingapp.components.Text(
                value = "Info",
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.subtitle1
            )

            com.angel.shoppingapp.components.Text(
                value = "Price",
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.h4
            )
            com.angel.shoppingapp.components.Text(
                value = "Info",
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.subtitle1
            )

            com.angel.shoppingapp.components.Text(
                value = "Description",
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.h4
            )
            com.angel.shoppingapp.components.Text(
                value = "Info",
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Composable
fun TopBar(title: String, navController: NavController, navigationIcon: @Composable() (() -> Unit)?) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    TopAppBar(
        title = {
            Text(text = title,
                color = Color.White)
        },
        backgroundColor = Color(0xFFB11E1E),
        navigationIcon = navigationIcon

    )
}

@Composable
fun SideMenuDrawer(list: List<com.angel.shoppingapp.model.Menu> = getMenu(),
                   state: DrawerState, navController: NavController){

    ModalDrawer(
        drawerState = state,
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
}



