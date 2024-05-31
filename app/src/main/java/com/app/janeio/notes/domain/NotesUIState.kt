package com.app.janeio.notes.domain

data class UIState(
    val isMainTopBarVisible:Boolean = true,
    val isBottomNavVisible:Boolean = true,
    val isNewNoteDialogOpen:Boolean = false,
    val showFloatButton:Boolean = true,
    val showBackTopBar:Boolean = false

)