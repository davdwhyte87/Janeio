package com.app.janeio.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotesViewModel::class.java)){
            return NotesViewModel() as T
        }

        throw java.lang.IllegalArgumentException("Unknown model")
    }
}