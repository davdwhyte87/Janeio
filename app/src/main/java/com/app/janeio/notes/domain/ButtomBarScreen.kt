package com.app.janeio.notes.domain


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen (
    val route:String,
    val title:String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews:Boolean,
    val badgeCount:Int?

){
    object Notes : BottomBarScreen(
        route = "notes",
        title = "Notes",
        selectedIcon = Icons.Filled.EditNote,
        unselectedIcon = Icons.Outlined.EditNote,
        hasNews = false,
        badgeCount = 0
    )

    object Todo : BottomBarScreen(
        route = "todo",
        title = "Todo",
        selectedIcon = Icons.Filled.FormatListNumbered,
        unselectedIcon = Icons.Outlined.FormatListNumbered,
        hasNews = false,
        badgeCount = 0
    )


}