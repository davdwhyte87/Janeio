package com.app.janeio.notes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.janeio.notes.domain.Folder
import com.app.janeio.notes.domain.FolderDao

import com.app.janeio.notes.domain.Note
import com.app.janeio.notes.domain.NoteDao


@Database(entities = [Note::class, Folder::class], version = 6, exportSchema = false)
//@TypeConverters(Converter::class)
abstract class NotesDatabase :RoomDatabase(){

    abstract fun notesDao(): NoteDao
    abstract fun folderDao(): FolderDao

    companion object{
        private var instance: NotesDatabase?=null

        @Synchronized
        fun getInstance(context:Context): NotesDatabase {
            if(instance ==null){
                instance = Room.databaseBuilder(context.applicationContext, NotesDatabase::class.java,
                "notes_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }

            return instance!!
        }

        private val roomCallback = object :Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

            }
        }

        private fun populateDatabase(db: NotesDatabase){
            val noteDao = db.notesDao()
            val folderDao = db.folderDao()
        }
    }
}