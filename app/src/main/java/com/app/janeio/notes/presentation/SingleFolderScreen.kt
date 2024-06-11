package com.app.janeio.notes.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.app.janeio.notes.domain.NotesViewModel
import com.app.janeio.notes.domain.UIState
import com.app.janeio.notes.presentation.components.AddNewNoteButton
import com.app.janeio.notes.presentation.components.BackTopBar
import com.app.janeio.notes.presentation.components.FolderFilesAppBar
import com.app.janeio.notes.presentation.components.GeneralScaffold
import com.app.janeio.notes.presentation.components.SingleFolderNotes
import com.app.janeio.notes.presentation.components.TopBar


@Composable
fun SingleFolderScreen(notesViewModel: NotesViewModel,
                       navController: NavHostController,
                       id:String
                       ){

    val uiState: UIState = notesViewModel.uiState.collectAsState().value

    GeneralScaffold(topBar = { FolderFilesAppBar(navController ) },
        floatingActionButton = { AddNewNoteButton(navController, id) }) {
        SingleFolderNotes(notesViewModel, uiState, navController, id.toInt() )
    }

}