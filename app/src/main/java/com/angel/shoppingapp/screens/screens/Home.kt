package com.angel.shoppingapp.screens.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.angel.shoppingapp.model.getMenu
import com.angel.shoppingapp.viewmodels.HomeModel
import com.angel.shoppingapp.widgets.DropDown
import com.angel.shoppingapp.widgets.MutableColumn
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