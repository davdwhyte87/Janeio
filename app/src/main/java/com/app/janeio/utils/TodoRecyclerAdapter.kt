package com.app.janeio.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.janeio.model.TodoItem
import com.app.janeio.view_models.TodoViewModel


//class TodoRecyclerAdapter(val viewModel:TodoViewModel, val arrayList:ArrayList<TodoItem>, val context: Context)
//    : RecyclerView.Adapter<TodoRecyclerAdapter.NotesViewHolder>(){
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteRecyclerAdapter.NotesViewHolder {
//        var root = LayoutInflater.from(parent.context).inflate(R.layout.item, parent,false)
//        return NotesViewHolder(root)
//    }
//
//    override fun onBindViewHolder(holder: NoteRecyclerAdapter.NotesViewHolder, position: Int) {
//        holder.bind(arrayList.get(position))
//    }
//
//    override fun getItemCount(): Int {
//        return arrayList.size
//    }
//
//
//    inner class TodoViewHolder( val xbinding: View): RecyclerView.ViewHolder(xbinding){
//        fun bind(blog:Blog){
//            xbinding.findViewById<TextView>(R.id.title).text = blog.title
//            xbinding.findViewById<Button>(R.id.delete).setOnClickListener{
//                viewModel.remove(blog)
//                notifyItemRemoved(arrayList.indexOf(blog))
//            }
//        }
//
//    }
//}