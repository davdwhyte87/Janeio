package com.app.janeio

import Janeio.R
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.janeio.components.cBackTopBar
import com.app.janeio.model.Note

import com.app.janeio.screens.BottomBarScreen
import com.app.janeio.screens.NotesScreen

import com.app.janeio.ui.theme.AppTheme
import com.app.janeio.ui.theme.Black2
import com.app.janeio.ui.theme.LightGrey
import com.app.janeio.ui.theme.LightPurple
import com.app.janeio.ui.theme.White
import com.app.janeio.view_models.AppViewModel
import com.app.janeio.view_models.NotesViewModel

import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow


data class BottomNavigationItem(
    val title:String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews:Boolean,
    val badgeCount:Int?
)


@AndroidEntryPoint
class MainActivity2: ComponentActivity() {

//    private val viewModelFactory = ViewModelFactory(this.application)
//    private val notesViewModel = ViewModelProvider(this, viewModelFactory).get(NotesViewModel::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val appViewModel:AppViewModel = hiltViewModel()

            AppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary,

                    ) {

                    Scaffold(
                        topBar = {
                            if(appViewModel.showTopNav.collectAsState().value){
                                topBar()
                            }
                            if(appViewModel.showBackAppBar.collectAsState().value){
                                cBackTopBar()
                            }
                                 },
                        modifier = Modifier
                            .padding(all = 0.dp)
                            .background(color = MaterialTheme.colorScheme.primary),
                        bottomBar = {
                            if (appViewModel.showButtomNav.collectAsState().value){
                                BottomAppBar(
                                    contentColor = MaterialTheme.colorScheme.primary,
                                    containerColor = MaterialTheme.colorScheme.primary ,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(all = 0.dp)
                                        .background(
                                            MaterialTheme.colorScheme.primary,

                                            )

                                ) {


                                    BottomNav(navController)
                                }
                            }

                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.tertiary,
                        floatingActionButton = {
                            if(appViewModel.showButtomNav.collectAsState().value){
                                AddButton {
                                    Log.d("OPen Dialog XXXXXX", "")

                                }
                            }

                        },

                        floatingActionButtonPosition = FabPosition.Center,
                    ) { it ->


                Column(
                    modifier = Modifier
                        .padding(it)
                        .padding(all = 15.dp)
                    ,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
//                    searchBar()
//
//                    //notesNFoldersList()
//                    notesNFoldersListPrev()
                      bottomNavGraph(navController = navController)

                }
                    }


                }
            }

        }
    }
}




//@Composable
//fun getNotesList(isPreview:Boolean):List<Note>{
//    val notesViewModel = hiltViewModel<NotesViewModel>()
//    if (isPreview) {
//        return listOf(
//            Note(
//                id = null,
//                CreatedAt = "",
//                FolderID = 0,
//                Note = "Notes sameple data",
//                Title = "This is dope",
//                UpdatedAt = "3:44pm",
//                Type = "fold"
//            )
//        )
//    }
//    return notesViewModel.notesList.collectAsState().value
//}

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
        selectedIconColor = White,
        unselectedIconColor = LightGrey,
        selectedTextColor = White,
        unselectedTextColor = LightGrey,
        disabledIconColor = White,
        disabledTextColor = White,
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



@Composable
fun AddButton(onClick: () -> Unit) {
    val appViewModel = hiltViewModel<AppViewModel>()

    FloatingActionButton(
        onClick = { appViewModel.openNewNotesDialog() },
        shape = CircleShape,
        contentColor = White,
        containerColor = LightPurple,
        modifier = Modifier.padding(all=0.dp)
    ) {
        Icon(Icons.Filled.Add, "Floating action button.", modifier = Modifier.padding(all = 0.dp))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBar(){
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.secondary

        ),
        title = {
            Row (verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Filled.FilterList, contentDescription = null)
                Text(text = "Notes", fontSize = 20.sp)
                Image(painter = painterResource(id = R.drawable.propic2), contentDescription =null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .padding(1.dp, 1.dp)
                        .size(50.dp, 50.dp),
                    contentScale = ContentScale.FillWidth)

            }
        })
}
