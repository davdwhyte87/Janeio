package com.app.janeio.notes.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.janeio.notes.domain.AppViewModel
import com.app.janeio.notes.domain.FileType
import com.app.janeio.notes.domain.Note
import com.app.janeio.notes.domain.NotesViewModel
import com.app.janeio.ui.theme.XWhite
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewNoteScreen (notesViewModel: NotesViewModel,
                   navController:NavHostController
){


    var title by rememberSaveable { mutableStateOf("") }
    val time by rememberSaveable {
        mutableStateOf("")
    }
    var noteData by rememberSaveable {
        mutableStateOf("")
    }

    val tempNoteData = notesViewModel.tempNote.collectAsState().value
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    val formatted = current.format(formatter)

//    if (appViewModel.saveNoteData.collectAsState().value){
//
//        var note = Note(
//            Title = title,
//            Note = noteData,
//            Type = "0",
//            CreatedAt = formatted,
//            UpdatedAt = formatted,
//            FolderID = null,
//            id = null
//        )
//        notesViewModel.add(note)
//        navController.navigate(BottomBarScreen.Notes.route)
//    }

    Column {

        TextField(
            value =title ,
            placeholder = { Text(text = "Title")},
            onValueChange = {
                            title = it
                var note = Note(
                    Title = title,
                    Note = noteData,
                    Type =   FileType.FILE.toString(),
                    CreatedAt = formatted,
                    UpdatedAt = formatted,
                    FolderID = null,
                    id = null
                )
                notesViewModel.updateTempNote(note)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = XWhite,
                focusedTextColor = XWhite,
                focusedPlaceholderColor = XWhite,
                unfocusedPlaceholderColor = XWhite,
            )
        )

        Text(text = "5:04 PM")
        TextField(
            value = noteData ,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {
                            noteData = it
                var note = Note(
                    Title = title,
                    Note = noteData,
                    Type =   FileType.FILE.toString(),
                    CreatedAt = formatted,
                    UpdatedAt = formatted,
                    FolderID = null,
                    id = null
                )
                notesViewModel.updateTempNote(note)
            },
            minLines = 20,
            maxLines = 50,
            placeholder = { Text(text = "Note Something down ....")}
        )
    }
}

