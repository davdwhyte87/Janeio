package com.app.janeio

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

@Composable
fun bottomNavGraph(navController:NavHostController){
    val appViewModel:AppViewModel = hiltViewModel()
   NavHost(navController = navController, startDestination = BottomBarScreen.Notes.route ) {

       composable(route=BottomBarScreen.Notes.route){
//
           NotesScreen(appViewModel, navController)
       }
       composable(route=BottomBarScreen.Todo.route){
           TodoScreen()
       }

       composable(route=NavScreen.NewNoteScreen.route){
           appViewModel.hideTopNav()
           appViewModel.hideButtomNav()
           appViewModel.showBackAppBar()
           NewNoteScreen()
       }
   }
}