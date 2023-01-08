package com.angel.shoppingapp.screens.screens

import DropDown
import MutableColumn
import SideMenuDrawer
import TopBar
import android.util.Log
import android.view.Surface
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
        topBar = { TopBar(drawerState = drawerState, scope = scope, title = "Home") },
        content = { it
            Surface(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(), color = Color(0xFFD5B79C)) {

                SideMenuDrawer(drawerState = drawerState, navController = navController) {
                    Column(verticalArrangement = Arrangement.Center) {
                        Spacer(modifier = Modifier.height(16.dp))
                        DropDown(homeModel.categoryListResponse, homeModel, isExpanded)

                        if (!isExpanded.value) {
                            MutableColumn(navController = navController,
                                homeModel.productListResponse)
                        }
                        MutableColumn(navController = navController, homeModel.productListResponse)
                    }
                }
            }
        })
}