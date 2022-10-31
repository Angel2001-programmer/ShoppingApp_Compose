package com.angel.shoppingapp.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ItemCard(item: String) {
        Card(modifier = Modifier.clip(shape = RoundedCornerShape(3, 3, 3, 3))) {
            Column() {
                AsyncImage(
                    modifier = Modifier.height(160.dp),
                    model = "https://www.foodbev.com/wp-content/gallery/food-releases-march-2019/Hazelnut-Spread-M-and-Ms.jpg",
                    contentDescription = ""
                )

                Card(shape = RoundedCornerShape(0, 0, 10, 10), elevation = 12.dp) {
                    Row(modifier = Modifier
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
fun MoreDetailsColumn(){
    Box(modifier = Modifier
        .padding(start = 8.dp)) {
        Column() {
            component.Text(
                value = "Title",
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.h4
            )
            component.Text(
                value = "Info",
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.subtitle1
            )

            component.Text(
                value = "Price",
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.h4
            )
            component.Text(
                value = "Info",
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.subtitle1
            )

            component.Text(
                value = "Description",
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.h4
            )
            component.Text(
                value = "Info",
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Composable
    fun TopBar(title: String) {
    TopAppBar(
        title = {
            Text(text = title,
                color = Color.White)
        },

        backgroundColor = Color(0xFFB11E1E),
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Menu,
                    contentDescription = "menu button",
                    tint = Color.White)
            }
        })
}

@Composable
    fun sidebar(item: String) {
    Surface(modifier = Modifier.background(Color.White)) {
        Surface(modifier = Modifier
            .fillMaxHeight()
            .width(200.dp)
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)) {
            Row(modifier = Modifier
                .clip(shape = RoundedCornerShape(15))
                .background(color = Color.LightGray)
                .width(370.dp)
                .size(150.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()) {
                    Text(text = item, style = TextStyle(fontSize = 25.sp))
                }
            }
        }
    }
}
