package com.angel.shoppingapp.screens.screens

import DropDown
import ErrorResponse
import MutableColumn
import SideMenuDrawer
import TopBar
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.angel.shoppingapp.viewmodels.HomeModel

@Composable
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val homeModel = viewModel<HomeModel>()
    val isExpanded = remember { mutableStateOf(false) }
    val apiResponseState = remember { mutableStateOf(true) }

    homeModel.getCategories()
    homeModel.getData()
    Log.d("HomeScreen", "HomeScreen: ${homeModel.productListResponse}")
    Log.d("HomeScreen", "HomeScreen: ${homeModel.chosenListResponse}")

    Scaffold(
        topBar = { TopBar(drawerState = drawerState, scope = scope, title = "Home") },
        content = { it
            Surface(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(), color = Color(0xFFD5B79C)) {

                SideMenuDrawer(drawerState = drawerState, navController = navController) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Spacer(modifier = Modifier.height(16.dp))
                        DropDown(homeModel.categoryListResponse, homeModel, isExpanded)
                        MutableColumn(navController = navController, homeModel.productListResponse)
                        if (!isExpanded.value) {
                            MutableColumn(navController = navController,
                                homeModel.chosenListResponse)
                            Log.d("Test", "HomeScreen: ${homeModel.chosenListResponse}")
                        }
                        if(!apiResponseState.value) {
                            ErrorResponse()
                        }
                    }
                }
            }
        })
}