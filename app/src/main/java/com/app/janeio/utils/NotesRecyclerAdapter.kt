package com.app.janeio.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.janeio.R
import com.app.janeio.model.Note
import com.app.janeio.view_models.NotesViewModel
import org.w3c.dom.Text


class NotesRecyclerAdapter(val viewModel:NotesViewModel, val arrayList:ArrayList<Note>,
                           val context: Context,
                           val onClickListener: OnClickListener
)
    : RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder>(){

    var onItemClick:((Note)->Unit)?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesRecyclerAdapter.NotesViewHolder {
        var root = LayoutInflater.from(parent.context).inflate(R.layout.notes_list_item, parent,false)
        return NotesViewHolder(root)
    }

    override fun onBindViewHolder(holder: NotesRecyclerAdapter.NotesViewHolder, position: Int) {
        holder.bind(arrayList.get(position))
        holder.xbinding.setOnClickListener {
            onClickListener.onClick(arrayList[position])
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    inner class NotesViewHolder( val xbinding: View): RecyclerView.ViewHolder(xbinding){
        init {
            xbinding.setOnClickListener {
                onItemClick?.invoke(arrayList[adapterPosition])
            }
        }
        fun bind(note:Note){
            xbinding.findViewById<TextView>(R.id.notes_recycler_item_title).text = note.Title
            xbinding.findViewById<TextView>(R.id.notes_recycler_item_info).text = note.Note
            xbinding.findViewById<TextView>(R.id.notes_recycler_item_date).text = note.CreatedAt


//            xbinding.findViewById<Button>(R.id.delete).setOnClickListener{
//                viewModel.remove(blog)
//                notifyItemRemoved(arrayList.indexOf(blog))
//            }
        }



    }

    class OnClickListener(val clickListener: (note: Note) -> Unit) {
        fun onClick(note: Note) = clickListener(note)
    }
}