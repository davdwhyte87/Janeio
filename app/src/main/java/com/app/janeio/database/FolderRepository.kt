package com.app.janeio.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.app.janeio.model.Folder
import com.app.janeio.model.FolderDao
import com.app.janeio.model.Note

class FolderRepository(application: Application) {
    private var folderDao:FolderDao

    private val database= NotesDatabase.getInstance(application)
    lateinit var allFolders: LiveData<MutableList<Folder>>
    init {
        folderDao=database.folderDao()
//        allNotes = noteDao.getAllNotes()
        allFolders= folderDao.getAllFolders()
    }

    suspend fun insert(folder:Folder){
        folderDao.insert(folder)
    }

    suspend fun getAll(): LiveData<MutableList<Folder>> {
        return folderDao.getAllFolders()
    }
}