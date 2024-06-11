package com.app.janeio.notes.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.janeio.notes.domain.NavScreen
import com.app.janeio.notes.domain.NotesViewModel
import com.app.janeio.notes.domain.UIState
import com.app.janeio.ui.theme.Typography
import com.app.janeio.ui.theme.XWhite


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopBar(navController: NavHostController,
               notesViewModel: NotesViewModel,
               uiState: UIState
){

    LaunchedEffect(Unit) {
        // reset temp note data to empty
        notesViewModel.resetTempNote()
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val tempNote = notesViewModel.tempNote.collectAsState().value
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.secondary
        ),
        title = {
            Row (verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(onClick = {

                    Log.d("Destination string XX", currentDestination?.route.toString())
                    when(currentDestination?.route.toString()){

                        NavScreen.NewNoteScreen.route->{
                            // appViewModel.saveNoteDataEvent()
                            if (!tempNote.Title.isBlank()){
                                notesViewModel.saveTempNote()
                            }
                            navController.popBackStack()
                        }

                        NavScreen.NewNoteScreen.route+"?id={id}"->{
                            // appViewModel.saveNoteDataEvent()
                            if (!tempNote.Title.isBlank()){
                                notesViewModel.saveTempNote()
                            }
                            navController.popBackStack()
                        }


                        NavScreen.SingleNoteScreen.route+"/{id}"->{
                            Log.d("Saving single note", "yes")
                            notesViewModel.saveSingleNote()
                            navController.popBackStack()
                        }
                        NavScreen.SingleFolderScreen.route+"/{id}"->{

                            navController.popBackStack()
                        }
                    }

                }) {
                    Icon(imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                        contentDescription = null,
                        tint = XWhite,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(text = "Notes", style = Typography.titleLarge, color = XWhite)
            }
        })
}