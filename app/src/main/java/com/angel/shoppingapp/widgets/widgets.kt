import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.angel.shoppingapp.R
import com.angel.shoppingapp.database.Basket
import com.angel.shoppingapp.model.CategoryItem
import com.angel.shoppingapp.model.getMenu
import com.angel.shoppingapp.model.productsItem
import com.angel.shoppingapp.moneyFormat
import com.angel.shoppingapp.navigation.Screen
import com.angel.shoppingapp.viewmodels.BasketModel
import com.angel.shoppingapp.viewmodels.HomeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun TopBar(
    drawerState: DrawerState,
    scope: CoroutineScope,
    title: String,
) {

    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
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
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "menu button",
                    tint = Color.White
                )
            }
        })
}

@Composable
fun SideMenuDrawer(
    drawerState: DrawerState,
    navController: NavController,
    content: @Composable() (() -> Unit),
) {
    val list: List<com.angel.shoppingapp.model.Menu> = getMenu()
    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            LazyColumn(modifier = Modifier.background(Color.LightGray)) {
                items(items = list, itemContent = { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp), verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = {
                                when (item.title) {
                                    "Home" -> {
                                        navController.navigate(Screen.HomeScreen.route)
                                    }
                                    "Basket" -> {
                                        navController.navigate(Screen.BasketScreen.route)
                                    }
                                    else ->
                                        Log.d(
                                            "Navigation",
                                            "SideMenuDrawer: Couldn't find a valid navigation route."
                                        )
                                }
                            }
                        ) {

                            Icon(
                                imageVector = item.icon,
                                contentDescription = "Home Icon",
                                tint = Color.Black,
                                modifier = Modifier.padding(end = 5.dp)
                            )
                            Text(
                                text = item.title,
                                color = Color.Black,
                                modifier = Modifier.weight(1f)
                            )
                        }

                    }

                })

            }

        },
        content = content
    )
}

@Composable
fun ItemCard(item: productsItem, navController: NavController) {
    Log.d("ItemCard", item.toString())
    Card(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(3, 3, 3, 3))
            .clickable
            {
                navController.navigate(Screen.Details.route + "/${item.id}")
                Log.d("onClick", "Column: ${item.id}")

            }, elevation = 16.dp
    ) {
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

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BasketCard(context: Context, list: List<Basket>) {
    val basketModel = BasketModel()
    val newList = mutableStateListOf<Basket>()

    newList.addAll(list)

    if(newList.isEmpty()){
        ErrorResponse()
    }

    LazyColumn {
        itemsIndexed(items = newList, key = { _, listItem ->
            listItem.hashCode()
        }) { _, item ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        newList.remove(item)
                        basketModel.removeProduct(context, item)
                        Toast.makeText(context, "Item removed from basket.", Toast.LENGTH_LONG)
                            .show()
                    }
                    true
                }
            )
            SwipeToDismiss(
                state = dismissState,
                background = {
                    val color = when (dismissState.dismissDirection) {
                        DismissDirection.StartToEnd -> Color.Transparent
                        DismissDirection.EndToStart -> Color.Red
                        null
                        -> Color.Blue
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = (10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete Icon",
                        )
                    }
                },
                dismissContent = {
                    BasketItem(dismissState, item)
                },
                directions = setOf(DismissDirection.EndToStart)
            )
            Divider()
        }
    }

    AlertDialog(
        title = "Purchase Successful",
        msg = "Thank you for choosing us to place your order with",
        confirmMSG = "Exit",
        newList = newList,
        homeModel = basketModel,
        context = context
    )
}

@Composable
fun ErrorResponse() {
        Box(contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painterResource(id = R.drawable.error),
                    contentDescription = "Error has happened.",
                    modifier = Modifier
                        .height(250.dp)
                        .width(250.dp),
                )
                Text(
                    text = "Error has happened\nplease report on google play.",
                    style = MaterialTheme.typography.h5, textAlign = TextAlign.Center
                )
            }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BasketItem(dismissState: DismissState, item: Basket) {
    Card(
        elevation = animateDpAsState(
            if (dismissState.dismissDirection != null) 4.dp else 0.dp
        ).value,
        modifier = Modifier
            .fillMaxWidth()
            .height(Dp(150f))
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = item.image,
                contentDescription = "product image",
                error = painterResource(id = R.drawable.placeholder)
            )
            Column(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(item.title!!)
                Text(text = moneyFormat(item.price!!))
            }
        }
    }
}


@Composable
fun AlertDialog(
    title: String,
    msg: String,
    confirmMSG: String,
    newList: MutableList<Basket>,
    homeModel: BasketModel,
    context: Context

) {
    var total = 0

    for (item in newList) {
        total = total + item.price!!
    }


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
                    TextButton(onClick = { openDialog.value = false }) {
                        Text(text = confirmMSG)
                    }
                }
            )
        }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column() {
                TextButton(modifier =
                Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEB5454)),
                    onClick = {
                        homeModel.clearAll(context)
                        newList.removeAll(newList)
                    }) {
                    Text(text = "Clear Basket", color = Color.Black)
                }
                TextButton(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray),
                    onClick = { openDialog.value = true },
                    content = {
                        Text("Total: ${moneyFormat(total)}", color = Color.Black)
                    }
                )
            }
        }
    }
}


@Composable
fun MoreDetailsColumn(list: List<productsItem>) {
    if(list.isEmpty()){
        ErrorResponse()
    }

    Box(
        modifier = Modifier
            .padding(start = 8.dp)
            .fillMaxWidth()
    ) {
        LazyColumn {
            items(list) { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(3, 3, 3, 3)),
                        elevation = 8.dp
                    ) {

                        AsyncImage(
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
                    value = moneyFormat(item.price),
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(list) { item ->
            Spacer(modifier = Modifier.height(14.dp))
            ItemCard(item = item, navController = navController)
        }
    }
}

@Composable
fun DropDown(data: List<CategoryItem>, homeModel: HomeModel, isExpanded: MutableState<Boolean>) {
    var title by remember { mutableStateOf("All") }

    Card(
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(6.dp)
    ) {
        TextButton(onClick = { isExpanded.value = !isExpanded.value }) {
            Surface(modifier = Modifier.width(260.dp)) {
                Column {
                    Row {
                        Text(title, textAlign = TextAlign.Start)
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "DropMenu"
                        )
                    }
                    if (isExpanded.value) {
                        //visible
                        Box(contentAlignment = Alignment.TopCenter) {
                            Spacer(modifier = Modifier.height(10.dp))
                            LazyColumn(content = {
                                items(items = data) { item ->
                                    TextButton(onClick = {
                                        title = item.name
                                        isExpanded.value = false
                                        homeModel.getChosenItems(item.id)
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