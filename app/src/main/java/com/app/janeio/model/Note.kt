package com.app.janeio.model

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


@Entity(tableName = "note_table")
class Note(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val Title: String,
    val Note: String,
    val Type: String,
    val CreatedAt: String,
    val UpdatedAt: String,
    val FolderID: Int?
)

enum class FileType{
    FILE, FOLDER
}


@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("delete from note_table")
    fun deleteAllNotes()


    @Query("select * from note_table")
    fun getAllNotes(): LiveData<MutableList<Note>>

    @Query("select * from note_table where id=:id")
    fun getSingle(id:Int):Flow<Note>
}
