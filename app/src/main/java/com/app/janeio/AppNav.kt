package com.app.janeio

import com.app.janeio.home.HomeScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.janeio.notes.domain.NavScreen
import com.app.janeio.notes.domain.NotesViewModel
import com.app.janeio.notes.presentation.NewNoteScreen
import com.app.janeio.notes.presentation.NotesScreen
import com.app.janeio.notes.presentation.SingleFolderScreen
import com.app.janeio.notes.presentation.SingleNoteScreen
import com.app.janeio.todo.presentation.TodoScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun appNav(navController:NavHostController, notesViewModel: NotesViewModel){

    NavHost(navController = navController, startDestination =com.app.janeio.notes.domain.NavScreen.NotesScreen.route ) {
        composable(route=NavScreen.HomeScreen.route){

            HomeScreen(navController)
        }


        composable(route=NavScreen.NotesScreen.route){
            notesViewModel.homeScreenUIState()
            NotesScreen(notesViewModel,navController)
        }

        composable(route=NavScreen.TodoScreen.route){
            TodoScreen()
        }
        
        composable(route=NavScreen.NewNoteScreen.route){
            notesViewModel.newNoteScreenUIState()
           NewNoteScreen(navController = navController, notesViewModel = notesViewModel)
        }

        composable(route=NavScreen.SingleFolderScreen.route+"/{id}"){navBackStack->
            notesViewModel.newNoteScreenUIState()
            val noteId = navBackStack.arguments?.getString("id")
            if (noteId !=null){
                SingleFolderScreen(navController = navController, notesViewModel = notesViewModel, id = noteId )
            }

        }

        composable(route= NavScreen.SingleNoteScreen.route+"/{id}"){ navBackStack->
//            backNavMode1(appViewModel)
            notesViewModel.updateNewNoteDialogUIState(false)
            notesViewModel.updateTopBarUIState(false)
            notesViewModel.updateButtomNavUIState(false)
            notesViewModel.updateShowFloatButton(false)
            notesViewModel.updateShowBackTopBar(true)
            val noteId = navBackStack.arguments?.getString("id")
            if (noteId !=null) {
                SingleNoteScreen(noteId, notesViewModel)
            }
        }
    }

}

