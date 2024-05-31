package com.app.janeio.notes.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.app.janeio.notes.domain.Note
import com.app.janeio.notes.domain.NoteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NoteRepository @Inject constructor(val appx: Application, val noteDao: NoteDao) {


    val database= NotesDatabase.getInstance(appx)
    lateinit var allNotes:Flow<List<Note>>
    init {
         //noteDao=database.notesDao()
//        allNotes = noteDao.getAllNotes()
          allNotes= noteDao.getAllNotes()

    }

    suspend fun insert(note:Note){
        noteDao.insert(note)
    }

    suspend fun delete(note: Note){
        noteDao.delete(note)
    }

    suspend fun getAll(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    fun getSingle(id:Int):Flow<Note>{
        return noteDao.getSingle(id)
    }
    fun getFolderFiles(id:Int):Flow<List<Note>>{
        return noteDao.getFolderFiles(id)
    }

     suspend fun update(note:Note){
        noteDao.update(note)
    }
    fun updatex(id:Int, body:String):Flow<Int>{
        return noteDao.updatex(id, body)
    }

}