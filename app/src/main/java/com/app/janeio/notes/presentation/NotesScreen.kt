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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import com.app.janeio.notes.domain.UIState
import com.app.janeio.notes.presentation.components.DeleteButton
import com.app.janeio.notes.presentation.components.NewFolderDialog
import com.app.janeio.notes.presentation.components.NotesDialog
import com.app.janeio.notes.presentation.components.NotesNFoldersList
import com.app.janeio.notes.presentation.components.SearchBar
import kotlinx.coroutines.flow.collectLatest


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotesScreen(
    notesViewModel: NotesViewModel,
    navController:NavHostController,
){
    val uiState: UIState = notesViewModel.uiState.collectAsState().value

//    LaunchedEffect(key1 = true) {
//        notesViewModel.clearMultiTempDeleteList()
//    }
    Column(
        modifier = Modifier.fillMaxHeight().padding(16.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ){

        SearchBar()
        DeleteButton(uiState, notesViewModel)
        NotesNFoldersList(navController, notesViewModel, uiState)


        NotesDialog(uiState = notesViewModel.uiState.collectAsState().value, navController=navController, notesViewModel = notesViewModel)
        NewFolderDialog(uiState = notesViewModel.uiState.collectAsState().value,
            notesViewModel)
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

