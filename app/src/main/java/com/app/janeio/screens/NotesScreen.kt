package com.app.janeio.screens

import Janeio.R
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.app.janeio.BottomNavigationItem
import com.app.janeio.bottomNavGraph
import com.app.janeio.components.NewFolderDialog
import com.app.janeio.components.NotesDialog
import com.app.janeio.model.Note

import com.app.janeio.ui.theme.AppTheme
import com.app.janeio.ui.theme.Black2
import com.app.janeio.ui.theme.LightGrey
import com.app.janeio.ui.theme.LightPurple
import com.app.janeio.ui.theme.XWhite
import com.app.janeio.view_models.AppViewModel
import com.app.janeio.view_models.NotesViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotesScreen(
appViewModel: AppViewModel,
notesViewModel: NotesViewModel,
navController:NavHostController
){


    Column(
        modifier = Modifier
            .padding(all = 15.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ){


        searchBar()
        notesNFoldersList()
        NotesDialog(appViewModel, navController)
        NewFolderDialog(appViewModel, notesViewModel)
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


@Composable
fun notesNFoldersList(){

    val notesViewModel = hiltViewModel<NotesViewModel>()
    notesViewModel.getAllNotes()

    //val notes = notesViewModel.notesList.collectAsState()
    val notes = notesViewModel.notesList.collectAsState()
    Log.d("NOTES COMP *******", notes.toString())

//    val filesNFolders = listOf()
    LazyColumn (verticalArrangement = Arrangement.spacedBy(20.dp)) {
        items(notes.value){item->
            NoteItemView(data = item)
        }
    }
}


@Preview
@Composable
fun notesNFoldersListPrev(){

    //val notesViewModel = hiltViewModel<NotesViewModel>()
    //notesViewModel.getAllNotes()

    //val notes = notesViewModel.notesList.collectAsState()



    val filesNFolders = listOf(
        Note(
            id = null,
            CreatedAt = "",
            FolderID = 0,
            Note = "Notes sameple data",
            Title = "This is dope",
            UpdatedAt = "3:44pm",
            Type = "fold"
        ),
        Note(
            id = null,
            CreatedAt = "",
            FolderID = 0,
            Note = "Notes sameple data",
            Title = "This is dope",
            UpdatedAt = "3:44pm",
            Type = "fold"
        )
    )
    LazyColumn (verticalArrangement = Arrangement.spacedBy(20.dp)) {
        items(filesNFolders){item->
            NoteItemView(data = item)
        }
    }
}



@Composable
fun NoteItemView(data: Note){

    Box (modifier = Modifier
        .fillMaxWidth()
        .background(Black2)
        .padding(10.dp)

    ) {
        Column {
            Text(text = data.Title, fontSize = 16.sp, color = XWhite)
            Text(text = data.Note, fontSize = 10.sp, color = LightGrey)

        }
    }

}