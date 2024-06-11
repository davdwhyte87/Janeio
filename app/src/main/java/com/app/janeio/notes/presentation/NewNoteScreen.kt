package com.app.janeio.notes.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.janeio.notes.domain.AppViewModel
import com.app.janeio.notes.domain.FileType
import com.app.janeio.notes.domain.Note
import com.app.janeio.notes.domain.NotesViewModel
import com.app.janeio.notes.domain.UIState
import com.app.janeio.notes.presentation.components.BackTopBar
import com.app.janeio.notes.presentation.components.GeneralScaffold
import com.app.janeio.ui.theme.Typography
import com.app.janeio.ui.theme.XWhite
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewNoteScreen (notesViewModel: NotesViewModel,
                   navController:NavHostController,
                   noteId:String?
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
    val vformatter = DateTimeFormatter.ofPattern("HH:mm")
    val vformatted = current.format(vformatter)
    val uiState: UIState = notesViewModel.uiState.collectAsState().value
    GeneralScaffold(topBar = { BackTopBar(navController, notesViewModel, uiState  ) }, floatingActionButton = {}, screenView = {
        Column (
            Modifier.padding(start = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
            ){

            TextField(
                value =title ,
                textStyle = Typography.titleLarge,
                placeholder = { Text(text = "Title", style = Typography.titleLarge)},
                onValueChange = {

                    title = it
                    var note = Note(
                        Title = title,
                        Note = noteData,
                        Type =   FileType.FILE.toString(),
                        CreatedAt = formatted,
                        UpdatedAt = formatted,
                        FolderID = if (noteId.isNullOrBlank()) null else noteId.toInt() ,
                        id = null
                    )
                    notesViewModel.updateTempNote(note)
                },
                modifier = Modifier.fillMaxWidth().padding(0.dp).offset(-20.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = XWhite,
                    focusedTextColor = XWhite,
                    focusedPlaceholderColor = XWhite,
                    unfocusedPlaceholderColor = XWhite,
                    cursorColor = XWhite,


                )
            )
            Text(text =vformatted, style = Typography.bodySmall, color = XWhite, modifier = Modifier.padding(top = 10.dp))
            TextField(
                value = noteData ,
                textStyle = Typography.titleLarge,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = XWhite,
                    focusedTextColor = XWhite,
                    focusedPlaceholderColor = XWhite,
                    unfocusedPlaceholderColor = XWhite,
                    cursorColor = XWhite
                ),
                modifier = Modifier.fillMaxWidth().padding(0.dp).offset(-20.dp),
                onValueChange = {
                    noteData = it
                    var note = Note(
                        Title = title,
                        Note = noteData,
                        Type =   FileType.FILE.toString(),
                        CreatedAt = formatted,
                        UpdatedAt = formatted,
                        FolderID =  if (noteId.isNullOrBlank()) null else noteId.toInt(),
                        id = null
                    )
                    notesViewModel.updateTempNote(note)
                },
                minLines = 20,
                maxLines = 50,
                placeholder = { Text(text = "Note Something down ....")}
            )
        }
    })

}

