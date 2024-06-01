package com.app.janeio.notes.domain

sealed class NavScreen(
    val route:String,
    val title:String
){
    object NewNoteScreen:NavScreen(
        route = "new_note",
        title = "New Note"
    )

    object SingleNoteScreen:NavScreen(
        route = "single_note",
        title = "Single Note"
    )

    object SingleFolderScreen:NavScreen(
        route = "single_folder",
        title = "Single Folder"
    )

    object HomeScreen:NavScreen(
        route = "home",
        title = "Home"
    )

    object NotesScreen:NavScreen(
        route = "notes",
        title = "Notes"
    )
    object TodoScreen:NavScreen(
        route = "todo",
        title = "Todo"
    )

}