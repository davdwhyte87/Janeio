package com.app.janeio.view_models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.janeio.database.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class AppViewModel @Inject constructor() :ViewModel(){

    private val _isNewNotesDialogOpen = MutableStateFlow(false)
    val isNewNotesDialogOpen = _isNewNotesDialogOpen.asStateFlow()

    private  val _showTopNav = MutableStateFlow(true)
    private val _showButtomNav = MutableStateFlow(true)

    val showTopNav = _showTopNav.asStateFlow()
    val showButtomNav = _showButtomNav.asStateFlow()

    private  val _showBackAppBar = MutableStateFlow(false)
    val showBackAppBar = _showBackAppBar.asStateFlow()

    private val _saveNoteData = MutableStateFlow(false)
    val saveNoteData = _saveNoteData.asStateFlow()


    private val _isNewFolderDialogOpen = MutableStateFlow(true)
    val isNewFolderDialogOpen = _isNewFolderDialogOpen.asStateFlow()


    fun newFolderDialog(data:Boolean){
        _isNewFolderDialogOpen.value = data
    }

    fun saveNoteDataEvent(){
        _saveNoteData.value = true
    }
    fun stopSaveNoteData(){
        _saveNoteData.value = false
    }
    fun openNewNotesDialog(){
        Log.d("ViewModelCalled *******", "")
        viewModelScope.launch {
            _isNewNotesDialogOpen.update { true }

        }

    }

    fun showTopNav(){
        _showTopNav.value = true
    }

    fun resetToHome(){
        _isNewNotesDialogOpen.value = false
        _saveNoteData.value = false
        _showBackAppBar.value= false
        _showButtomNav.value = true
        _showTopNav.value = true
    }
    fun hideTopNav(){
        _showTopNav.value = false
    }

    fun showBackAppBar(){
        _showBackAppBar.value = true
    }

    fun hideBackAppBar(){
        _showBackAppBar.value = false
    }

    fun showButtomNav(){
        _showButtomNav.value = true
    }

    fun hideButtomNav(){
        _showButtomNav.value = false
    }

    fun onEvent(event: NotesScreenUIEvents){

        when(event){
            is NotesScreenUIEvents.OpenNotesDialog->{
                _isNewNotesDialogOpen.update {
                    event.data
                }

            }
        }
    }

    fun closeNewNotesDialog(){
        viewModelScope.launch {
            _isNewNotesDialogOpen.emit(false)
        }
    }

}

sealed interface NotesScreenUIEvents{
    data class  OpenNotesDialog(val data:Boolean):NotesScreenUIEvents
}