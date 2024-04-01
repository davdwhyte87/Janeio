package com.app.janeio

import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.app.janeio.screens.HomeScreen
import com.app.janeio.ui.theme.Black2
import com.app.janeio.ui.theme.LightGrey
import com.app.janeio.ui.theme.LightPurple
import com.app.janeio.ui.theme.White


data class BottomNavigationItem(
    val title:String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews:Boolean,
    val badgeCount:Int?
)



class MainActivity2: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var selectedItem = 0

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {

                Scaffold(

                    modifier = Modifier.padding(),
                    bottomBar = {BottomAppBar () {



                        BottomNav()
                    }},
                    floatingActionButton = {
                     AddButton {

                     }
                    },
                    floatingActionButtonPosition = FabPosition.Center,

                ) {it ->  
                    
                }
            }

        }
    }
}


@Composable
fun BottomNav(){
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
        selectedIconColor = White,
        unselectedIconColor = LightGrey,
        selectedTextColor = White,
        unselectedTextColor = LightGrey,
        disabledIconColor = White,
        disabledTextColor = White,
        selectedIndicatorColor = Black2
    )


    NavigationBar(containerColor = Black2, contentColor = White,  ){

        navItems.forEachIndexed{ index, item ->
            Log.d("Item name ...", item.title)

            NavigationBarItem(
                colors = navColors,
                label = { Text(text = item.title) },
                selected = (index == selectedItem) ,
                onClick = {
                    selectedItem = index
                },
                alwaysShowLabel = true,
                icon = {
                    androidx.compose.material3.Icon(
                        imageVector = if (index == selectedItem) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                        modifier = Modifier.size(34.dp)

                    )
                })

        }
    }
}


@Composable
fun AddButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        shape = CircleShape,
        contentColor = White,
        containerColor = LightPurple,
        modifier = Modifier.padding(all=0.dp)
    ) {
        Icon(Icons.Filled.Add, "Floating action button.", modifier = Modifier.padding(all = 0.dp))
    }
}