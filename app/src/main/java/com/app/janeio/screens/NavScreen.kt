package com.app.janeio.screens

sealed class NavScreen(
    val route:String,
    val title:String
){
    object NewNoteScreen:NavScreen(
        route = "new_note",
        title = "New Note"
    )
}