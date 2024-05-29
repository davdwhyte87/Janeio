package com.app.janeio.notes.presentation.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.janeio.model.FileType
import com.app.janeio.notes.domain.NavScreen
import com.app.janeio.notes.domain.NotesViewModel
import com.app.janeio.notes.presentation.NoteItemView
import com.app.janeio.view_models.AppViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesNFoldersList(appViewModel: AppViewModel, navController: NavHostController){
    val notesViewModel = hiltViewModel<NotesViewModel>()
    notesViewModel.getAllNotes()

    //val notes = notesViewModel.notesList.collectAsState()
    val notes = notesViewModel.notesList.collectAsState()
    Log.d("NOTES COMP *******", notes.toString())

//    val filesNFolders = listOf()
    LazyColumn (verticalArrangement = Arrangement.spacedBy(20.dp)) {
        items(notes.value){item->
            NoteItemView(data = item,
                modifier = Modifier.combinedClickable (
                    onClick = {
                        Log.d("FType XXXX", item.Type)
                        // navigate to a single note view
                        if(item.Type == FileType.FILE.name){

                            navController.navigate(NavScreen.SingleNoteScreen.route+"/${item.id}")
                        }
                        // navigate to single folder screen
                        if (item.Type == FileType.FILE.name){

                        }
                    },
                    onLongClick = {
                        Log.d("Long Press XXXX", "pressed")
                        appViewModel.notesListCheckBox(true)
                    }
                ), appViewModel, notesViewModel
            )
        }
    }
}