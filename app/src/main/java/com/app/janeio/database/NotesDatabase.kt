package com.app.janeio.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.janeio.model.Note
import com.app.janeio.model.NoteDao


@Database(entities = [Note::class], version = 3)
abstract class NotesDatabase :RoomDatabase(){

    abstract fun notesDao(): NoteDao

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
        }
    }
}