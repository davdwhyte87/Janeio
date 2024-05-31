package com.app.janeio.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.app.janeio.notes.data.NoteRepository
import com.app.janeio.notes.data.NotesDatabase
import com.app.janeio.notes.domain.NoteDao
import com.app.janeio.notes.domain.NoteDao_Impl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//singleton for whole app
//activity for activity
//view model
// retained for screen rotation

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
     fun getRepo(app:Application, noteDao: NoteDao): NoteRepository {
         return NoteRepository(app, noteDao)
     }

//    @Provides
//    @Singleton
//    fun getApplication( @ApplicationContext app:Context):Application{
//        return app as Application
//    }

    @Provides
    @Singleton
    fun notesDao(db: NotesDatabase):NoteDao{
        return NoteDao_Impl(db)
    }

    @Provides
    @Singleton
    fun notesDatabase(@ApplicationContext context:Context):NotesDatabase{
        return  Room.databaseBuilder(context.applicationContext, NotesDatabase::class.java,
            "notes_database")
            .fallbackToDestructiveMigration()
            .build()

    }

}