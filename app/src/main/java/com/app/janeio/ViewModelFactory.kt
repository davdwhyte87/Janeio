package com.app.janeio.view_models

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.app.janeio.notes.data.NoteRepository
import com.app.janeio.notes.domain.NotesViewModel
import javax.inject.Inject


class ViewModelFactory @Inject constructor( val application: Application, val noteRepository: NoteRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotesViewModel::class.java)){
            return NotesViewModel(application, noteRepository) as T
        }

//        else if(modelClass.isAssignableFrom(FolderViewModel::class.java)){
//            return FolderViewModel(application) as T
//        }

        throw java.lang.IllegalArgumentException("Unknown model")
    }
}