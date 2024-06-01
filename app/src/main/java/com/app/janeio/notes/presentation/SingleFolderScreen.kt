package com.app.janeio.notes.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.app.janeio.notes.domain.NotesViewModel
import com.app.janeio.notes.domain.UIState
import com.app.janeio.notes.presentation.components.SingleFolderNotes


@Composable
fun SingleFolderScreen(notesViewModel: NotesViewModel,
                       navController: NavHostController,
                       id:String
                       ){

    val uiState: UIState = notesViewModel.uiState.collectAsState().value
    SingleFolderNotes(notesViewModel, uiState, navController, id.toInt() )
}