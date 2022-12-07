package com.angel.shoppingapp.widgets

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.angel.shoppingapp.R
import com.angel.shoppingapp.model.Category
import com.angel.shoppingapp.model.productsItem
import com.angel.shoppingapp.navigation.Screen
import com.angel.shoppingapp.screens.screens.list
import com.angel.shoppingapp.viewmodels.DetailsModel
import com.angel.shoppingapp.viewmodels.HomeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

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

@Composable
fun ItemCard(item: productsItem, navController: NavController) {
    var detailsModel = viewModel<DetailsModel>()

    Log.d("ItemCard", item.toString())

    Card(modifier = Modifier
        .clip(shape = RoundedCornerShape(3, 3, 3, 3))
        .clickable
        {
            navController.navigate(Screen.Details.route)
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BasketCard(list: List<productsItem>) {
    var total = remember {
        mutableStateOf(0)
    }

    val itemList = remember {
        mutableStateListOf<productsItem>()
    }

    LazyColumn {
        items(items = list, itemContent = { item ->
            val dismissState = rememberDismissState()

            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
//                list.remove(item)
            }

            SwipeToDismiss(
                state = dismissState,
                modifier = Modifier.padding(
                    vertical = Dp(1f)),
                directions = setOf(
                    DismissDirection.EndToStart
                ),
                dismissThresholds = { direction ->
                    FractionalThreshold(if (direction == DismissDirection.EndToStart) 0.1f else 0.05f)
                },
                background = {
                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> Color.White
                            else -> Color.Red
                        }
                    )
                    val alignment = Alignment.CenterEnd
                    val icon = Icons.Default.Delete

                    val scale by animateFloatAsState(
                        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                    )

                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = Dp(20f)),
                        contentAlignment = alignment
                    ) {
                        Icon(
                            icon,
                            contentDescription = "Delete Icon",
                            modifier = Modifier.scale(scale)
                        )
                    }
                },
                dismissContent = {
                    Card(
                        elevation = animateDpAsState(
                            if (dismissState.dismissDirection != null) 4.dp else 0.dp
                        ).value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dp(150f))
                            .align(alignment = Alignment.CenterVertically)
                    ) {
                        Row(modifier = Modifier.padding(8.dp)) {
                            AsyncImage(
                                model = item.images[0],
                                contentDescription = "product image"
                            )
                            Column(
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                                verticalArrangement = Arrangement.SpaceBetween) {
                                Text(text = item.title)
                                Text(text = "£${item.price}")
                            }
                        }
                    }
                })
            Divider(modifier = Modifier.height(1.dp))
        })

    }
    AlertDialog(
        title = "Purchase Successful",
        msg = "Thank you for choosing us to place your order with",
        confirmMSG = "Exit",
        total = total.value
    )
}

@Composable
fun AlertDialog(
    title: String,
    msg: String,
    confirmMSG: String,
    total: Int,
) {

    Column {
        val openDialog = remember { mutableStateOf(false) }
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = title)
                },
                text = {
                    Text(text = msg)
                },
                confirmButton = {
                    Button(onClick = {
                        openDialog.value = false
                    }) {
                        Text(text = confirmMSG)
                    }
                }
            )
        }

        Box(contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
            com.angel.shoppingapp.components.Button(onClick = { openDialog.value = true },

                elevation = ButtonDefaults.elevation(2.dp),
                value = "Total ${moneyFormat(total)}")
        }
    }
}


@Composable
fun MoreDetailsColumn(list: List<productsItem>) {
    Box(modifier = Modifier
        .padding(start = 8.dp)
        .fillMaxWidth()) {
        LazyColumn {
            items(list) { item ->
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Card(modifier = Modifier
                        .clip(shape = RoundedCornerShape(3, 3, 3, 3)),
                        elevation = 8.dp) {

                        AsyncImage(
                            modifier = Modifier.height(190.dp),
                            model = item.images[0],
                            contentDescription = "Product image",
                            error = painterResource(id = R.drawable.placeholder),
                        )
                    }
                }
                com.angel.shoppingapp.components.Text(
                    value = "Title:",
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.h6
                )
                com.angel.shoppingapp.components.Text(
                    value = item.title,
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.subtitle1
                )

                com.angel.shoppingapp.components.Text(
                    value = "Price:",
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.h6
                )
                com.angel.shoppingapp.components.Text(
                    value = item.price.toString(),
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.subtitle1
                )

                com.angel.shoppingapp.components.Text(
                    value = "Description:",
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.h6
                )
                com.angel.shoppingapp.components.Text(
                    value = item.description,
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.subtitle1
                )
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

//    Box(contentAlignment = Alignment.BottomCenter,
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight()) {
//    com.angel.shoppingapp.components.Button(onClick = { /*TODO*/ },
//        elevation = ButtonDefaults.elevation(2.dp),
//        value = "Total ${moneyFormat(total)}")


}


@Composable
fun DropDown(data: List<Category>, homeModel: HomeModel, isExpanded: MutableState<Boolean>) {
    var title by remember { mutableStateOf("All") }

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
                                                title = "Clothes"
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
                    }
                }
            }
        }
    }
}





