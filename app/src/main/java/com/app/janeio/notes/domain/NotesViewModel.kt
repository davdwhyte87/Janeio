package com.app.janeio.notes.domain




import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.app.janeio.notes.data.NoteRepository
import com.app.janeio.notes.data.NotesDatabase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import javax.inject.Inject





@HiltViewModel
class NotesViewModel @Inject constructor(application: Application, val noteRepository: NoteRepository) :AndroidViewModel(application) {
    private var noteDao:NoteDao
    private var database: NotesDatabase
    //private val noteRepository: NoteRepository
    val defNote = Note(null,"","","","","",null)
    var defNotesList:List<Note> = (listOf(defNote))
    private val _notelist = MutableStateFlow(defNotesList)
    var notesList = _notelist.asStateFlow()

    private val _tempNote = MutableStateFlow(defNote)
    val tempNote = _tempNote.asStateFlow()

    private val _tempMultiDeleteNotes = MutableStateFlow(listOf(defNote))
    val tempMultiDeleteNotes = _tempMultiDeleteNotes.asStateFlow()


    private val _uiState = MutableStateFlow(UIState())
    val uiState = _uiState.asStateFlow()

//    var newList :MutableList<Note>

    private val _singleNote= MutableStateFlow(defNote)
    val singleNote = _singleNote.asStateFlow()
    private val _folderFiles = MutableStateFlow(defNotesList)
    val folderFiles = _folderFiles.asStateFlow()


    private val _eventFlow = MutableSharedFlow<NotesUIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        database = NotesDatabase.getInstance(application)
        noteDao = database.notesDao()

        // noteRepository = NoteRepository(application)

    }
    //    @OptIn(DelicateCoroutinesApi::class)
    fun add(item: Note){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                noteRepository.insert(item)
            }
        }
//        newList.add(item)
//        notesList.value = newList
        getAllNotes()

    }
    fun saveTempNote(){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                noteRepository.insert(_tempNote.value)
                resetTempNote()
            }
        }
    }

    fun changeSingleNote(note:Note){
        _singleNote.value = note
    }

    fun saveSingleNote(){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                noteRepository.update(_singleNote.value)
            }
        }
    }

    fun resetTempNote(){
        viewModelScope.launch{
            _tempNote.value =Note(null,"","","","","",null)
        }
    }


    fun multiDelete(){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                val notes = _tempMultiDeleteNotes.value
                notes.forEach {
                    noteRepository.delete(it)
                }
                _tempMultiDeleteNotes.value = listOf()
            }
        }

    }

    fun updateMultiTempDeleteLIst(note:Note){
        val list =  _tempMultiDeleteNotes.value
        val newList = list.toMutableList()
        newList.add(note)
        _tempMultiDeleteNotes.value = newList
        Log.d("Update List", _tempMultiDeleteNotes.value.toString())
    }
    fun removeFromMultiTempDeleteList(note:Note){
        val list =  _tempMultiDeleteNotes.value
        val newList = list.toMutableList()
        newList.remove(note)
        _tempMultiDeleteNotes.value = newList
    }

    fun updateTempNote(note:Note){
        _tempNote.value = note
    }

    fun update(note:Note){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                noteRepository.update(note)
            }

            //getSingle(note.id!!)
        }

    }

    fun remove(item: Note){

        //notesList.value?.remove(item)
    }

    //    @OptIn(DelicateCoroutinesApi::class)
    fun getAllNotes(){
        viewModelScope.launch {
            val temp = noteRepository.getAll().first()
            // hide notes that have association with a folder
            val newNoteList = ArrayList<Note>()
            temp.forEach {
                if(it.FolderID ==null){
                    newNoteList.add(it)
                }
            }
            _notelist.value = newNoteList
//            if (tempList != null) {
//                tempList.forEach {
//                    if (it.FolderID != null){
//                        tempList.remove(it)
//                    }
//                }
//            }
//            notesList.value = noteRepository.getAll()
        }

    }


    fun getSingle(id:Int){
        viewModelScope.launch {
            _singleNote.value = noteRepository.getSingle(id).first()
        }
    }

    fun getFolderFiles(id:Int){
        viewModelScope.launch {
            _folderFiles.value = noteRepository.getFolderFiles(id).first()
        }
    }


    fun updateTopBarUIState(data:Boolean){
        _uiState.update {
            it.copy(isMainTopBarVisible = data)
        }
    }

    fun updateButtomNavUIState(data:Boolean){
        _uiState.update {
            it.copy(isBottomNavVisible = data)
        }
    }
    fun updateNewNoteDialogUIState(data:Boolean){
        _uiState.update {
            it.copy(isNewNoteDialogOpen = data)
        }
    }

    fun updateShowFloatButton(data:Boolean){
        _uiState.update {
            it.copy(showFloatButton = data)
        }
    }
    fun updateShowBackTopBar(data:Boolean){
        _uiState.update {
            it.copy(showBackTopBar = data)
        }
    }
    fun updateShowNoteListCheckBox(data:Boolean){
        _uiState.update {
            it.copy(showNotListCheckBox = data)
        }
    }

    fun homeScreenUIState(){
        _uiState.update {
            it.copy(showBackTopBar = false,
                showFloatButton = true,
                isBottomNavVisible = true,
                isMainTopBarVisible = true,
                showNotListCheckBox = false
            )
        }
    }

    fun newNoteScreenUIState(){
        _uiState.update {
            it.copy(showBackTopBar = true,
                showFloatButton = false,
                isBottomNavVisible = false,
                isMainTopBarVisible = false,
                showNotListCheckBox = false
            )
        }
    }

    fun updateShowNewFolderDialogState(data:Boolean){
        _uiState.update { it.copy(showNewFolderDialog = data) }
    }

     fun onEvents(event:NotesUIEvent){
        when(event){
            is NotesUIEvent.showNewNoteDialog->{

                viewModelScope.launch {
                    _eventFlow.emit(NotesUIEvent.showNewNoteDialog)
                }


            }
            is NotesUIEvent.hideNewNoteDialog->{

            }
        }
    }

}


sealed class  NotesUIEvent{
    object showNewNoteDialog:NotesUIEvent()
    object hideNewNoteDialog:NotesUIEvent()
}
