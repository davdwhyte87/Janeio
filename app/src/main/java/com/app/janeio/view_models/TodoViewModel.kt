package com.app.janeio.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.janeio.model.TodoItem

class TodoViewModel:ViewModel() {
    var todoList =MutableLiveData<ArrayList<TodoItem>>()
    var newList = arrayListOf<TodoItem>()


    fun add(todoItem: TodoItem){
        newList.add(todoItem)
        todoList.value = newList

    }

    fun remove(todoItem: TodoItem){
        newList.remove(todoItem )
        todoList.value = newList
    }
}