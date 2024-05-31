package com.app.janeio.notes.presentation.components



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.Alignment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import com.app.janeio.notes.domain.AppViewModel
import com.app.janeio.notes.domain.FileType
import com.app.janeio.notes.domain.Note

import com.app.janeio.notes.domain.NotesViewModel
import com.app.janeio.ui.theme.LightPurple
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewFolderDialog(appViewModel: AppViewModel, notesViewModel:NotesViewModel){
    var folderName by rememberSaveable {
        mutableStateOf("")
    }

    val newFolderDialogOpen = appViewModel.isNewFolderDialogOpen.collectAsState().value

    when {
        newFolderDialogOpen ->{
            Dialog(
                onDismissRequest = {
                    appViewModel.closeNewNotesDialog()
                    appViewModel.newFolderDialog(false)
                },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false,
                    dismissOnClickOutside = true
                )
            ) {

                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth(0.99f)
                        .fillMaxHeight(0.3f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary)

                ){

                    Column {
                        Text(text = "Add Folder")
                        TextField(
                            value = folderName,
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = {
                                folderName = it
                            }
                        )

                        Button(
                            onClick = {
                                val current = LocalDateTime.now()
                                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                                val formatted = current.format(formatter)

                                // new folder note
                                var note = Note(
                                    Title = folderName,
                                    Note = "",
                                    Type =   FileType.FOLDER.toString(),
                                    CreatedAt = formatted,
                                    UpdatedAt = formatted,
                                    FolderID = null,
                                    id = null
                                )
                                notesViewModel.add(note)
                                appViewModel.newFolderDialog(false)
                            },
                            shape = RectangleShape,
                            modifier = Modifier.align(Alignment.End),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = LightPurple
                            )
                        ) {
                            Text(text = "Create")
                        }
                    }

                }

            }
        }
    }

}