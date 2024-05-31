package com.app.janeio.notes.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.janeio.notes.domain.AppViewModel
import com.app.janeio.notes.domain.FileType


import com.app.janeio.notes.domain.NavScreen
import com.app.janeio.notes.domain.Note
import com.app.janeio.notes.domain.NotesUIEvent

import com.app.janeio.ui.theme.Black2
import com.app.janeio.ui.theme.LightGrey
import com.app.janeio.ui.theme.XWhite

import com.app.janeio.notes.domain.NotesViewModel
import com.app.janeio.notes.presentation.components.NewFolderDialog
import com.app.janeio.notes.presentation.components.NotesDialog
import com.app.janeio.notes.presentation.components.NotesNFoldersList
import com.app.janeio.notes.presentation.components.SearchBar
import kotlinx.coroutines.flow.collectLatest


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotesScreen(
    appViewModel: AppViewModel = hiltViewModel(),
    notesViewModel: NotesViewModel,
    navController:NavHostController
){


    Column(
        modifier = Modifier
            .padding(all = 15.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ){

        SearchBar()
        DeleteButton(appViewModel, notesViewModel)
        NotesNFoldersList(appViewModel, navController)
        NotesDialog(uiState = notesViewModel.uiState.collectAsState().value, navController=navController, notesViewModel = notesViewModel)
        NewFolderDialog(appViewModel, notesViewModel)
    }
}



@Composable
fun DeleteButton(appViewModel: AppViewModel, notesViewModel: NotesViewModel){
    val isListCheckBox = appViewModel.isNotesListCheckBox.collectAsState().value
    Column (modifier = Modifier.fillMaxWidth()){

        if(isListCheckBox){
            IconButton(
                onClick = {
                    Log.d("Delete Button clicked", "clicked")
                    notesViewModel.multiDelete()
                    appViewModel.notesListCheckBox(false)
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .background(color = Black2)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    modifier = Modifier.size(24.dp),
                    contentDescription ="",
                    tint = XWhite
                )
            }
        }

    }

}

@Composable
fun searchBar(){
    var text by rememberSaveable {
        mutableStateOf("")
    }

    TextField(
        value = text,
        onValueChange = {text = it},
        label = { Text(text = "Search") },
        leadingIcon = {
            Icon(
                Icons.Outlined.Search, contentDescription = null,
            modifier = Modifier.size(35.dp)
        )
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary, shape = RectangleShape)
            .border(width = 2.dp, color = MaterialTheme.colorScheme.secondary)
        ,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.secondary,
            focusedLeadingIconColor = MaterialTheme.colorScheme.secondary,
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.secondary,
            unfocusedTextColor = MaterialTheme.colorScheme.secondary

        )
    )
}

data class NoteItem(
    val title:String,
    val summary:String,
    val type: Int
)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun notesNFoldersList(appViewModel: AppViewModel, navController: NavHostController){
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


//@OptIn(ExperimentalFoundationApi::class)
//@Preview
//@Composable
//fun notesNFoldersListPrev(){
//
//    //val notesViewModel = hiltViewModel<NotesViewModel>()
//    //notesViewModel.getAllNotes()
//
//    //val notes = notesViewModel.notesList.collectAsState()
//
//
//
//    val filesNFolders = listOf(
//        Note(
//            id = null,
//            CreatedAt = "",
//            FolderID = 0,
//            Note = "Notes sameple data",
//            Title = "This is dope",
//            UpdatedAt = "3:44pm",
//            Type = "fold"
//        ),
//        Note(
//            id = null,
//            CreatedAt = "",
//            FolderID = 0,
//            Note = "Notes sameple data",
//            Title = "This is dope",
//            UpdatedAt = "3:44pm",
//            Type = "fold"
//        )
//    )
//    LazyColumn (verticalArrangement = Arrangement.spacedBy(20.dp)) {
//        items(filesNFolders){item->
//            NoteItemView(data = item,
//                modifier = Modifier.combinedClickable (
//                    onClick = {},
//                    onLongClick = {
//                        Log.d("Long Press XXXX", "pressed")
//
//                    }
//                ),
//                app
//            )
//        }
//    }
//}



@Composable
fun NoteItemView(data: Note, modifier: Modifier,
                 appViewModel: AppViewModel ,
                 notesViewModel: NotesViewModel
){

    Box (modifier = Modifier
        .fillMaxWidth()
        .background(Black2)
        .padding(10.dp)

    ) {
        Column (modifier = modifier ) {


            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                var isChecked by rememberSaveable {
                    mutableStateOf(false)
                }

                Text(text = data.Title, fontSize = 16.sp, color = XWhite)
                val isListCheckBox = appViewModel.isNotesListCheckBox.collectAsState().value
                if (isListCheckBox){
                    Checkbox(
                        modifier = Modifier,
                        checked = isChecked,
                        onCheckedChange = {
                            if (isChecked) {
                                isChecked = false
                                notesViewModel.removeFromMultiTempDeleteList(data)
                            }else{
                                isChecked = true
                                notesViewModel.updateMultiTempDeleteLIst(data)
                            }

                                          },
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = XWhite,

                            )
                    )
                }

            }

            Text(
                text = data.Note,
                fontSize = 10.sp,
                color = LightGrey,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (data.Type == FileType.FOLDER.name){
                Icon(
                    imageVector = Icons.Outlined.Folder,
                    contentDescription = "" ,
                    tint = XWhite
                )
            }


        }
    }

}