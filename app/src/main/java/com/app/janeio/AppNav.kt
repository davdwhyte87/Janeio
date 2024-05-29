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
import com.app.janeio.screens.BottomBarScreen
import com.app.janeio.screens.NavScreen
import com.app.janeio.screens.NewNoteScreen
import com.app.janeio.screens.NotesScreen
import com.app.janeio.screens.SingleNoteScreen
import com.app.janeio.screens.TodoScreen
import com.app.janeio.view_models.AppViewModel
import com.app.janeio.view_models.NotesViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun appNav(navController:NavHostController){

    NavHost(navController = navController, startDestination =com.app.janeio.notes.domain.NavScreen.HomeScreen.route ) {
        composable(route=com.app.janeio.notes.domain.NavScreen.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(route=BottomBarScreen.Notes.route){


        }
        composable(route=BottomBarScreen.Todo.route){
            TodoScreen()
        }

        composable(route=NavScreen.NewNoteScreen.route){

        }

        composable(route=NavScreen.SingleNoteScreen.route+"/{id}"){navBackStack->
//            backNavMode1(appViewModel)
            val noteId = navBackStack.arguments?.getString("id")
//            if (noteId !=null) {
//                SingleNoteScreen(noteId, notesViewModel)
//            }
        }
    }

}