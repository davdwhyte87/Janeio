package com.app.janeio.notes.domain

import androidx.lifecycle.LiveData
import androidx.room.*


@Entity(tableName = "folder_table")
data class Folder(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val Name:String,
    val CreatedAt: String,
    val UpdatedAt: String
)

@Dao
interface FolderDao {

    @Insert
    fun insert(note: Folder)

    @Update
    fun update(note: Folder)

    @Delete
    fun delete(note: Folder)

    @Query("delete from folder_table")
    fun deleteAllFolders()


    @Query("select * from folder_table")
    fun getAllFolders(): LiveData<MutableList<Folder>>
}