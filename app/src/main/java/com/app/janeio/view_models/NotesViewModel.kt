package com.app.janeio.view_models



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.janeio.model.Note
import com.app.janeio.model.TodoItem

class NotesViewModel:ViewModel() {
    var notesList =MutableLiveData<ArrayList<Note>>()
    var newList = arrayListOf<Note>()


    fun add(item: Note){
        newList.add(item)
        notesList.value = newList

    }

    fun remove(item: Note){
        newList.remove(item )
        notesList.value = newList
    }
}