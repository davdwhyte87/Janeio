package com.app.janeio.notes.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.janeio.BottomNavigationItem
import com.app.janeio.screens.BottomBarScreen
import com.app.janeio.ui.theme.Black2
import com.app.janeio.ui.theme.LightGrey
import com.app.janeio.ui.theme.XWhite

@Composable
fun BottomNav(navController: NavHostController){

    val screens = listOf(
        BottomBarScreen.Notes,
        BottomBarScreen.Todo
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navItems = listOf(
        BottomNavigationItem(
            title = "Notes",
            selectedIcon = Icons.Filled.EditNote,
            unselectedIcon = Icons.Outlined.EditNote,
            hasNews = false,
            badgeCount = 0
        ),
        BottomNavigationItem(
            title = "Todo",
            selectedIcon = Icons.Filled.FormatListNumbered,
            unselectedIcon = Icons.Outlined.FormatListNumbered,
            hasNews = false,
            badgeCount = 0
        ),

        )

    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    val navColors = NavigationBarItemColors(
        selectedIconColor = XWhite,
        unselectedIconColor = LightGrey,
        selectedTextColor = XWhite,
        unselectedTextColor = LightGrey,
        disabledIconColor = XWhite,
        disabledTextColor = XWhite,
        selectedIndicatorColor = Black2
    )


    NavigationBar(containerColor = Black2,   ){

        screens.forEachIndexed{ index, item ->


            NavigationBarItem(
                colors = navColors,
                label = { Text(text = item.title) },
                selected =currentDestination?.hierarchy?.any{
                    it.route == item.route
                }==true,
                onClick = {
                    Log.d("Item name ...", item.title)
                    selectedItem = index
                    navController.navigate(item.route)
                },

                alwaysShowLabel = true,
                icon = {
                    Icon(
                        imageVector = if (index == selectedItem) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                        modifier = Modifier.size(34.dp)

                    )
                })

        }
    }
}