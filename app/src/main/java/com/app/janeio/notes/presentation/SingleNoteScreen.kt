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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.janeio.notes.domain.Note
import com.app.janeio.notes.domain.NotesViewModel

import com.app.janeio.ui.theme.XWhite

import kotlinx.coroutines.flow.collect
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SingleNoteScreen(id:String, notesViewModel: NotesViewModel){

    //
    val singleNote = notesViewModel.singleNote.collectAsState()
    //val currentNoteState = singleNote
//    var title by rememberSaveable { mutableStateOf("Hello") }
    var time by rememberSaveable {
        mutableStateOf("")
    }
//    var noteData by rememberSaveable {
//        mutableStateOf("")
//    }
    val noteData =singleNote.value.Note
    val title = singleNote.value.Title

    Log.d("ID SINGLE NOTE XX", singleNote.value.id.toString())
    //notesViewModel.getSingle(id.toInt())


//    title = "singleNote.Title"
//    noteData = "singleNote.Note"
//    time =" singleNote.UpdatedAt"

    LaunchedEffect(key1 = true) {
        notesViewModel.getSingle(id.toInt())
        //notesViewModel.getSingle(id.toInt())
        //title = singleNote.value.Title
    }



    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    val formatted = current.format(formatter)

    Column {
        TextField(
            value =title ,
            placeholder = { Text(text = "Title") },
            onValueChange = {
                val note = Note(
                    Title = it,
                    Note = singleNote.value.Note,
                    Type =   singleNote.value.Type,
                    CreatedAt = singleNote.value.CreatedAt,
                    UpdatedAt = formatted,
                    FolderID = singleNote.value.FolderID,
                    id = singleNote.value.id
                )
                notesViewModel.changeSingleNote(note)
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

                val note = Note(
                    Title = singleNote.value.Title,
                    Note = it,
                    Type =   singleNote.value.Type,
                    CreatedAt = singleNote.value.CreatedAt,
                    UpdatedAt = formatted,
                    FolderID = singleNote.value.FolderID,
                    id = singleNote.value.id
                )
                notesViewModel.changeSingleNote(note)
//                notesViewModel.updateTempNote(note)
            },
            minLines = 20,
            maxLines = 50,
            placeholder = { Text(text = "Note Something down ....") }
        )
    }
}