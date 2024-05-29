package com.app.janeio.notes.domain




import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.app.janeio.notes.data.NoteRepository
import com.app.janeio.database.NotesDatabase
import com.app.janeio.model.Note
import com.app.janeio.model.NoteDao
import com.app.janeio.model.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import javax.inject.Inject



@HiltViewModel
class NotesViewModel @Inject constructor(application: Application, val noteRepository: NoteRepository) :AndroidViewModel(application) {
    private var noteDao:NoteDao
    private var database:NotesDatabase
    //private val noteRepository: NoteRepository
    val defNote = Note(null,"","","","","",null)
    var defNotesList:List<Note> = (listOf(defNote))
    private val _notelist = MutableStateFlow(defNotesList)
    var notesList = _notelist.asStateFlow()

    private val _tempNote = MutableStateFlow(defNote)
    val tempNote = _tempNote.asStateFlow()

    private val _tempMultiDeleteNotes = MutableStateFlow(listOf(defNote))
    val tempMultiDeleteNotes = _tempMultiDeleteNotes.asStateFlow()

//    var newList :MutableList<Note>

    private val _singleNote= MutableStateFlow(defNote)
    val singleNote = _singleNote.asStateFlow()
    private val _folderFiles = MutableStateFlow(defNotesList)
    val folderFiles = _folderFiles.asStateFlow()
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


}

