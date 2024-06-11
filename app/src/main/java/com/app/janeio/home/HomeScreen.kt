package com.app.janeio.home

import Janeio.R
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.janeio.appNav
import com.app.janeio.notes.domain.BottomNavItem
import com.app.janeio.notes.domain.BottomNavigationItem
import com.app.janeio.notes.domain.NavScreen
import com.app.janeio.notes.domain.NotesViewModel
import com.app.janeio.notes.domain.UIState
import com.app.janeio.notes.presentation.NotesScreen
import com.app.janeio.notes.presentation.components.AddButton
import com.app.janeio.notes.presentation.components.BackTopBar
import com.app.janeio.notes.presentation.components.BottomNav
import com.app.janeio.notes.presentation.components.TopBar
import com.app.janeio.todo.presentation.TodoScreen
import com.app.janeio.ui.theme.Black1
import com.app.janeio.ui.theme.Black2
import com.app.janeio.ui.theme.LightGrey
import com.app.janeio.ui.theme.LightPurple
import com.app.janeio.ui.theme.XWhite



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController:NavHostController, notesViewModel: NotesViewModel){
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val navItems = listOf(
        BottomNavItem(
            title = "Notes",
            selectedIcon = Icons.Filled.EditNote,
            unselectedIcon = Icons.Outlined.EditNote,
            hasNews = false,
            badgeCount = 0,
            route = NavScreen.NotesScreen.route
        ),
        BottomNavItem(
            title = "Todo",
            selectedIcon = Icons.Filled.FormatListNumbered,
            unselectedIcon = Icons.Outlined.FormatListNumbered,
            hasNews = false,
            badgeCount = 0,
            route = NavScreen.TodoScreen.route
        ),

        )
    val pagerState:PagerState = rememberPagerState {
        navItems.size
    }
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        if (!pagerState.isScrollInProgress) {
                selectedTabIndex = pagerState.currentPage
        }
    }



    Column  {
        HorizontalPager(state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

        ) {
            if (it==0){
                NotesScreen(notesViewModel = notesViewModel, navController = navController)
            }
            if (it==1){
                TodoScreen()
            }
        }
        TabRow(selectedTabIndex = selectedTabIndex,
            containerColor = Black2,
            contentColor = XWhite,

        ) {
            navItems.fastForEachIndexed { i, item ->
                Tab(
                    selected = i == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = i
                              },
                    text = { Text(text = item.title)},
                    icon = {
                        Icon(imageVector = if(i == selectedTabIndex){
                            item.selectedIcon
                        }else {item.unselectedIcon}, contentDescription = null )
                    }
                )
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeWarapper(navController: NavHostController, notesViewModel: NotesViewModel){
    val uiState:UIState = notesViewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
                TopBar()
        },
        modifier = Modifier
            .padding(all = 0.dp)
            .background(color = MaterialTheme.colorScheme.primary),
        bottomBar = {

        },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.tertiary,
        floatingActionButton = {

                AddButton(
                    viewModel = notesViewModel)

        },

        floatingActionButtonPosition = FabPosition.Center,
    ) { it ->


        Column(
            modifier = Modifier
                .padding(it)
                .padding(all = 8.dp)
            ,
            verticalArrangement = Arrangement.spacedBy(1.dp),
        ) {

            HomeScreen(navController, notesViewModel )

        }
    }
}









