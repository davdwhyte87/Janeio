package com.app.janeio.utils

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.app.janeio.R
import com.app.janeio.model.FileType
import com.app.janeio.model.Note
import com.app.janeio.view_models.NotesViewModel


class NotesRecyclerAdapter(
    val viewModel:NotesViewModel, val arrayList: Array<Note>,
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
            val folderIcon = xbinding.findViewById<ImageView>(R.id.folder_icon)
            val noteView = xbinding.findViewById<LinearLayout>(R.id.single_note_view)
            if (note.Type == FileType.FILE.toString()){
                folderIcon.visibility = GONE
            }

            val colorPick =(1..3).random()
            when(colorPick){
                1->noteView.background.setTint(ContextCompat.getColor(context, R.color.yellow))
                2->noteView.background.setTint(ContextCompat.getColor(context, R.color.seal))
                3->noteView.background.setTint(ContextCompat.getColor(context, R.color.light_grey))
            }
//            noteView.background.setTint(ContextCompat.getColor(context, R.color.yellow))
//            if(note.Type == FileType.FILE.toString() && note.FolderID !=null){
//
//            }
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