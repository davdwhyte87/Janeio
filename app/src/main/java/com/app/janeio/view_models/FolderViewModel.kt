package com.app.janeio.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.app.janeio.database.FolderRepository
import com.app.janeio.database.NoteRepository
import com.app.janeio.database.NotesDatabase
import com.app.janeio.model.Folder
import com.app.janeio.model.Note
import com.app.janeio.model.NoteDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FolderViewModel(application: Application):AndroidViewModel(application) {

    private var noteDao: NoteDao
    private var database: NotesDatabase
    private val folderRepository: FolderRepository

    lateinit var folderList : LiveData<MutableList<Folder>>
//    var newList :MutableList<Note>

    init {
        database = NotesDatabase.getInstance(application)
        noteDao = database.notesDao()
        folderRepository = FolderRepository(application)
        folderList = folderRepository.allFolders.asFlow().asLiveData()
    }
    //    @OptIn(DelicateCoroutinesApi::class)
    fun add(item: Folder){
        GlobalScope.launch{
            folderRepository.insert(item)
        }
//        newList.add(item)
//        notesList.value = newList
        getAllNotes()

    }

    fun remove(item: Folder){

        folderList.value?.remove(item)
    }

    //    @OptIn(DelicateCoroutinesApi::class)
    fun getAllNotes(){
        GlobalScope.launch {
            folderList = folderRepository.getAll()
        }

    }
}