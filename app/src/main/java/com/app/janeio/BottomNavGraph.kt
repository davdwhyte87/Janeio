package com.app.janeio

import android.os.Build
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
           appViewModel.hideTopNav()
           appViewModel.hideButtomNav()
           appViewModel.showBackAppBar()
           NewNoteScreen(appViewModel, notesViewModel, navController)
       }
   }
}