package com.app.janeio.notes.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.janeio.notes.domain.AppViewModel
import com.app.janeio.notes.domain.NotesViewModel
import com.app.janeio.notes.domain.UIState
import com.app.janeio.ui.theme.Black2
import com.app.janeio.ui.theme.XWhite


@Composable
fun DeleteButton(uiState: UIState,
                 notesViewModel: NotesViewModel){
    val isListCheckBox = uiState.showNotListCheckBox
    Column (modifier = Modifier.fillMaxWidth()){

        if(isListCheckBox){
            IconButton(
                onClick = {
                    Log.d("Delete Button clicked", "clicked")
                    notesViewModel.multiDelete()
                    notesViewModel.clearMultiTempDeleteList()
                    notesViewModel.updateShowNoteListCheckBox(false)

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