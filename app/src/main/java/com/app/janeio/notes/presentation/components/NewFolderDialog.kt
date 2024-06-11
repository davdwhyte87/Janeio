package com.app.janeio.notes.presentation.components



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
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
import com.app.janeio.notes.domain.UIState
import com.app.janeio.ui.theme.Black2
import com.app.janeio.ui.theme.DarkGrey
import com.app.janeio.ui.theme.LightGrey
import com.app.janeio.ui.theme.LightPurple
import com.app.janeio.ui.theme.Typography
import com.app.janeio.ui.theme.XWhite
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewFolderDialog(uiState: UIState, notesViewModel:NotesViewModel){
    var folderName by rememberSaveable {
        mutableStateOf("")
    }


    when {
        uiState.showNewFolderDialog ->{
            Dialog(
                onDismissRequest = {
                                   notesViewModel.updateShowNewFolderDialogState(false)
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

                    Column (
                        modifier=Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Text(text = "Add Folder", style = Typography.titleLarge, color= XWhite)
                        TextField(
                            value = folderName,
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = {
                                folderName = it
                            },
                            colors = TextFieldDefaults.colors().copy(
                                unfocusedContainerColor = XWhite,
                                focusedContainerColor = XWhite,
                                unfocusedTextColor = Black2,
                                focusedTextColor = Black2,
                                focusedPlaceholderColor = DarkGrey,
                                unfocusedPlaceholderColor = DarkGrey,
                                cursorColor = Black2,

                                ),

                        )

                        Button(
                            onClick = {
                                val current = LocalDateTime.now()
                                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                                val formatted = current.format(formatter)

                                // new folder note
                                val note = Note(
                                    Title = folderName,
                                    Note = "",
                                    Type =   FileType.FOLDER.toString(),
                                    CreatedAt = formatted,
                                    UpdatedAt = formatted,
                                    FolderID = null,
                                    id = null
                                )
                                notesViewModel.add(note)
                                notesViewModel.updateShowNewFolderDialogState(false)
                            },
                            shape = RectangleShape,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
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