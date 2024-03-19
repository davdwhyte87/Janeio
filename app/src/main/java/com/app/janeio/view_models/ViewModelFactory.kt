package com.app.janeio.view_models

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class ViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotesViewModel::class.java)){
            return NotesViewModel(application) as T
        }

        else if(modelClass.isAssignableFrom(FolderViewModel::class.java)){
            return FolderViewModel(application) as T
        }

        throw java.lang.IllegalArgumentException("Unknown model")
    }
}