package com.example.carecircle.Presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LocalPharmacy
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.carecircle.Presentation.History.HistoryScreen
import com.example.carecircle.Presentation.Pills.AddPillScreen
import com.example.carecircle.Presentation.Pills.PillsScreen
import com.example.carecircle.Presentation.Today.MedicineViewModel
import com.example.carecircle.Presentation.Today.TodayScreen

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    object Home : BottomNavItem("today", "Today", Icons.Outlined.CalendarMonth, Icons.Filled.CalendarMonth)
    object History : BottomNavItem("history", "History", Icons.Outlined.WatchLater, Icons.Filled.WatchLater)
    object Pills : BottomNavItem("pills", "Pills", Icons.Outlined.LocalPharmacy, Icons.Filled.LocalPharmacy)
}
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.History,
        BottomNavItem.Pills
    )
    val medViewModel = viewModel<MedicineViewModel>()


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (currentRoute == item.route) item.selectedIcon else item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) }
                    )
                }
            }
        } , topBar = {
            TopAppBar(
                title = { Text("My App") }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(it)
        ) {
            composable(BottomNavItem.Home.route) { TodayScreen( medicineViewModel= medViewModel) }
            composable(BottomNavItem.History.route) { HistoryScreen(medicineViewModel = medViewModel) }
            composable(BottomNavItem.Pills.route) { PillsScreen(medicineViewModel = medViewModel,navController) }
            composable("addpill") { AddPillScreen(medViewModel,navController) }
        }
    }
}
