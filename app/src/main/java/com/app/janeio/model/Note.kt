package com.app.janeio.model

import androidx.lifecycle.LiveData
import androidx.room.*


@Entity(tableName = "note_table")
class Note(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val Title: String,
    val Note: String,
    val CreatedAt: String,
    val UpdatedAt: String
)

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
}
