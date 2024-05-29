package com.app.janeio

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
fun bottomNavGraph(navController:NavHostController){
    val appViewModel:AppViewModel = hiltViewModel()
    val notesViewModel:NotesViewModel = hiltViewModel()
   NavHost(navController = navController, startDestination = BottomBarScreen.Notes.route ) {

       composable(route=BottomBarScreen.Notes.route){
          appViewModel.resetToHome()
           NotesScreen(appViewModel,notesViewModel, navController)
       }
       composable(route=BottomBarScreen.Todo.route){
           TodoScreen()
       }

       composable(route=NavScreen.NewNoteScreen.route){
          backNavMode1(appViewModel)
           NewNoteScreen(appViewModel, notesViewModel, navController)
       }

       composable(route=NavScreen.SingleNoteScreen.route+"/{id}"){navBackStack->
           backNavMode1(appViewModel)
           val noteId = navBackStack.arguments?.getString("id")
           if (noteId !=null) {
               SingleNoteScreen(noteId, notesViewModel)
           }
       }
   }

}


// sets up nav by hiding navs we do not need and showing those that we need
fun backNavMode1(appViewModel:AppViewModel){
    appViewModel.hideTopNav()
    appViewModel.hideButtomNav()
    appViewModel.showBackAppBar()
}