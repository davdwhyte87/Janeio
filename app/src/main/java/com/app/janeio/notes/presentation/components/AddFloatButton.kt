package com.app.janeio.notes.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.janeio.notes.domain.AppViewModel
import com.app.janeio.notes.domain.NotesUIEvent
import com.app.janeio.notes.domain.NotesViewModel
import com.app.janeio.notes.domain.UIState
import com.app.janeio.ui.theme.LightPurple
import com.app.janeio.ui.theme.XWhite


@Composable
fun AddButton(viewModel: NotesViewModel) {

//    val d = notesViewModel.uiState.collectAsState().value.toString()
    FloatingActionButton(
        onClick = {
//            Log.d("OPen Dialog XXXXXX", d)
            viewModel.onEvents(NotesUIEvent.showNewNoteDialog)
            viewModel.updateNewNoteDialogUIState(true)
        },
        shape = CircleShape,
        contentColor = XWhite,
        containerColor = LightPurple,
        modifier = Modifier.padding(all=0.dp)
    ) {
        Icon(Icons.Filled.Add, "Floating action button.", modifier = Modifier.padding(all = 0.dp))
    }
}