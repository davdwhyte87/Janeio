package com.app.janeio.model

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Entity(tableName = "note_table")
class Note(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val Title: String,
    val Note: String,
    val Type: String,
    val CreatedAt: String,
    val UpdatedAt: String,
    var FolderID: Int?
)

enum class FileType{
    FILE, FOLDER
}


@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(note: Note)

    @Query("UPDATE note_table SET Note=:noteBody WHERE id=:id")
    fun updatex(id:Int, noteBody:String):Flow<Int> = MutableStateFlow(id)

    @Delete
    fun delete(note: Note)

    @Query("delete from note_table")
    fun deleteAllNotes()


    @Query("select * from note_table ")
    fun getAllNotes(): Flow<List<Note>>

    @Query("select * from note_table where id=:id")
    fun getSingle(id:Int):Flow<Note>

    @Query("select * from note_table where FolderID=:id")
    fun getFolderFiles(id:Int):Flow<List<Note>>
}
