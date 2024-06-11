package com.app.janeio

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.janeio.home.HomeScreen
import com.app.janeio.notes.domain.NotesUIEvent
import com.app.janeio.notes.domain.NotesViewModel
import com.app.janeio.notes.presentation.components.AddButton
import com.app.janeio.notes.presentation.components.BackTopBar
import com.app.janeio.notes.presentation.components.TopBar
import com.app.janeio.ui.theme.AppTheme
import com.app.janeio.ui.theme.Black2
import com.app.janeio.ui.theme.LightGrey
import com.app.janeio.ui.theme.XWhite
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MainActivity3 : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            val notesViewModel:NotesViewModel = hiltViewModel()
            val uiState = notesViewModel.uiState.collectAsState().value


            LaunchedEffect(key1 = true) {
                notesViewModel.eventFlow.collectLatest {event->
                    when(event){
                        is NotesUIEvent.showNewNoteDialog->{
                            Log.d("GIRL BINGGE XXXXX"," ")
                        }

                        NotesUIEvent.hideNewNoteDialog -> TODO()
                    }

                }
            }
            AppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary,

                    ) {


                    Scaffold(
                        topBar = {
                            if(uiState.isMainTopBarVisible){
                                TopBar()
                            }
                            if (uiState.showBackTopBar){
                                BackTopBar(navController = navController,
                                    notesViewModel = notesViewModel,
                                    uiState = uiState)
                            }
                        },
                        modifier = Modifier
                            .padding(all = 0.dp)
                            .background(color = MaterialTheme.colorScheme.primary),
                        bottomBar = {
                            if(uiState.isBottomNavVisible) {
                                BottomAppBar(
                                    contentColor = MaterialTheme.colorScheme.primary,
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(all = 0.dp)
                                        .background(
                                            MaterialTheme.colorScheme.primary,

                                            )

                                ) {


                                    com.app.janeio.notes.presentation.components.BottomNav(
                                        navController
                                    )
                                }
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.tertiary,
                        floatingActionButton = {
                            if (uiState.showFloatButton){
                                AddButton(
                                    viewModel = notesViewModel)
                            }

                        },

                        floatingActionButtonPosition = FabPosition.Center,
                    ) { it ->


                        Column(
                            modifier = Modifier
                                .padding(it)
                                .padding(all = 8.dp)
                            ,
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {


                            appNav(navController = navController, notesViewModel)

                        }
                    }
                }
            }
        }
    }
}



