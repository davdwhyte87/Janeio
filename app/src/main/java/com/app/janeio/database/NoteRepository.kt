package com.app.janeio.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.app.janeio.model.Note
import com.app.janeio.model.NoteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class NoteRepository(application: Application) {
    private var noteDao:NoteDao

    private val database= NotesDatabase.getInstance(application)
    lateinit var allNotes:LiveData<MutableList<Note>>
    init {
        noteDao=database.notesDao()
//        allNotes = noteDao.getAllNotes()
          allNotes= noteDao.getAllNotes()

    }

    suspend fun insert(note:Note){
        noteDao.insert(note)
    }

    suspend fun getAll(): LiveData<MutableList<Note>> {
        return noteDao.getAllNotes()
    }

    fun getSingle(id:Int):Flow<Note>{
        return noteDao.getSingle(id)
    }

     suspend fun update(note:Note){
        noteDao.update(note)
    }
    fun updatex(id:Int, body:String):Flow<Int>{
        return noteDao.updatex(id, body)
    }

}