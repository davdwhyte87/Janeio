package com.app.janeio.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun NewNoteScreen (){
    var title by rememberSaveable { mutableSetOf("") }
    Column {


        TextField(value =title , onValueChange = {})
    }
}