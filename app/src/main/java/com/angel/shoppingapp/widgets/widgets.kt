package com.angel.shoppingapp.widgets

import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.angel.shoppingapp.R
import com.angel.shoppingapp.model.productsItem
import com.angel.shoppingapp.navigation.Screen
import com.angel.shoppingapp.screens.screens.list
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

@Composable
fun ItemCard(item: productsItem, navController: NavController) {
    Log.d("ItemCard", item.toString())

    Card(modifier = Modifier
        .clip(shape = RoundedCornerShape(3, 3, 3, 3))
        .clickable
        {
            navController.navigate("detail_screen/" + item.id)
            Log.d("onClick", "Column: ${item.id}")
        }, elevation = 16.dp) {
        Column {
            AsyncImage(
                modifier = Modifier.height(190.dp),
                model = item.images[0],
                contentDescription = "",
                error = painterResource(id = R.drawable.placeholder)
            )

            Card(shape = RoundedCornerShape(0, 0, 10, 10), elevation = 16.dp) {
                Row(
                    modifier = Modifier
                        .height(70.dp)
                        .width(253.dp)
                        .background(Color(0xFFBFBDBD)),
                    verticalAlignment = Alignment.CenterVertically
                )

                {
                    Text(
                        text = item.title,
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .background(Color(0xFFBFBDBD))
                            .weight(1F),
                        style = MaterialTheme.typography.subtitle1,
                    )

                    Text(
                        text = "Price: ${moneyFormat(item.price)}",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(Color(0xFFBFBDBD))
                            .weight(1F),
                        style = MaterialTheme.typography.subtitle1,
                        textAlign = TextAlign.End
                    )

                }
            }

        }

    }
}

fun moneyFormat(num: Int): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.UK)
    numberFormat.maximumFractionDigits
    val convert = numberFormat.format(num)

    return convert
}


//@Composable
//fun BasketCard(item: productsItem) {
//    var total = 0.00
//
//    LazyColumn(modifier = Modifier.fillMaxWidth()) {
//        items(items = list, itemContent = { item ->
//
//            Row(modifier = Modifier.padding(8.dp)) {
//                AsyncImage(model = item., contentDescription = "product image")
//                Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
//                    Text(text = item.title)
//                    Text(text = "£${item.price}")
//                }
//            }
//        })
//    }
//
//    Box(contentAlignment = Alignment.BottomCenter,
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight()) {
//        com.angel.shoppingapp.components.Button(onClick = { /*TODO*/ },
//            elevation = ButtonDefaults.elevation(2.dp),
//            value = "Total £$total")
//    }
//}

@Composable
fun MoreDetailsColumn() {
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
fun TopBar(
    drawerState: DrawerState,
    scope: CoroutineScope,
    title: String,
) {

    TopAppBar(
        title = {
            Text(text = title,
                color = Color.White)
        },
        backgroundColor = Color(0xFFB11E1E),
        navigationIcon = {
            IconButton(onClick = {
                if (drawerState.isClosed) {
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
            }
        })
}

@Composable
fun SideMenuDrawer(
    drawerState: DrawerState,
    navController: NavController,
    content: @Composable() (() -> Unit),
) {

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
                            onClick = {
                                when (item.title) {
                                    "Home" -> {
                                        navController.navigate(Screen.HomeScreen.route)
                                    }
                                    "Basket" -> {
                                        navController.navigate(Screen.BasketScreen.route)
                                    }
                                    else ->
                                        Log.d("Navigation",
                                            "SideMenuDrawer: Couldn't find a valid navigation route.")
                                }
                            }
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
        content = content)
}



