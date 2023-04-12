package com.app.janeio.view_models



import android.app.Application
import androidx.lifecycle.*
import com.app.janeio.database.NoteRepository
import com.app.janeio.database.NotesDatabase
import com.app.janeio.model.Note
import com.app.janeio.model.NoteDao
import com.app.janeio.model.TodoItem
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) :AndroidViewModel(application) {
    private var noteDao:NoteDao
    private var database:NotesDatabase
    private val noteRepository: NoteRepository

    lateinit var notesList : LiveData<MutableList<Note>>
//    var newList :MutableList<Note>

    init {
        database = NotesDatabase.getInstance(application)
        noteDao = database.notesDao()
        noteRepository = NoteRepository(application)
        notesList = noteRepository.allNotes.asFlow().asLiveData()
    }
//    @OptIn(DelicateCoroutinesApi::class)
    fun add(item: Note){
        GlobalScope.launch{
            noteRepository.insert(item)
        }
//        newList.add(item)
//        notesList.value = newList
        getAllNotes()

    }

    fun remove(item: Note){

        notesList.value?.remove(item)
    }

//    @OptIn(DelicateCoroutinesApi::class)
    fun getAllNotes(){
        GlobalScope.launch {
            notesList = noteRepository.getAll()
        }

    }


}

