package com.app.janeio

import Janeio.R
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.janeio.database.NoteRepository
import com.app.janeio.model.FileType
import com.app.janeio.model.Note
import com.app.janeio.utils.NotesRecyclerAdapter
import com.app.janeio.view_models.NotesViewModel

import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SingleFolderActivity @Inject constructor() : AppCompatActivity() {
    lateinit var toolbar:Toolbar
    lateinit var backBtn:ImageButton
    lateinit var notesRecycler:RecyclerView
    private var viewManager = LinearLayoutManager(this)
    private lateinit var viewModel: NotesViewModel
    lateinit var viewedNote:Note
    var noteId =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_folder)

        toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.back_toolbar)
        setSupportActionBar(toolbar)



        notesRecycler = findViewById(R.id.folder_notes_recycler)
            //setup viee model
//        val viewModelFactory = ViewModelFactory(application, noteRepository = )

        val viewModel:NotesViewModel by viewModels()

        // get single folder fata
        checkNoteData()

        // observe data from flow
        observeData()
        initializeAdapter()
        viewModel.getFolderFiles(noteId)

        // onclick listeners
        val newFileBtn = findViewById<FloatingActionButton>(R.id.create_file_in_folder)
        newFileBtn.setOnClickListener {
            val intent = Intent(applicationContext, SingleNoteActivity::class.java)
            intent.putExtra("noteId", viewedNote.id)
            intent.putExtra("newFileFromFolder", 1)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val actionBar = supportActionBar
        if (actionBar != null) {
            //Log.i("XXXX", "aactionbae")
            actionBar.setDisplayHomeAsUpEnabled(true)
            val actionBarView = layoutInflater.inflate(R.layout.action_bar_back, null)
            actionBar.setCustomView(actionBarView)
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
            backBtn= actionBarView.findViewById<ImageButton>(R.id.back_btn)
            backBtn.setOnClickListener{
                //Log.i("XXXXXXX","HHHHHHHHH")
                finish()
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {

        return super.onCreateView(parent, name, context, attrs)

    }

    override fun onResume() {
        super.onResume()
        //observeData()
        Log.i("XXXXXX", "Resumed activity!!! +"+noteId)
        viewModel.getFolderFiles(noteId)
    }


    fun initializeAdapter(){
        notesRecycler.layoutManager = viewManager

    }

    fun observeData(){
        lifecycleScope.launch {
        viewModel.folderFiles.collectLatest {
            Log.i("XXXXXX", "not list data changed")
            val itArray =it.toTypedArray()
            notesRecycler.adapter = NotesRecyclerAdapter(viewModel,  itArray, applicationContext, NotesRecyclerAdapter.OnClickListener{
                    note -> openSingleNote(note)
            })
        }
        }
        // observe single note data (folder)

        lifecycleScope.launch {
            viewModel.singleNote.collectLatest {
                // get and save single note data
                Log.i("XXXXXXX", "single folder note id "+it.id)
                viewedNote = it
                // get the files in this folder if Id is not null or 0
                if (it.id !=null){
                    viewModel.getFolderFiles(it.id)
                }
            }
        }


//        viewModel.notesList.observe(this, {
//            Log.i("data", it.toString())
//            notesRecycler.adapter = NotesRecyclerAdapter(viewModel, it as ArrayList<Note>, this, NotesRecyclerAdapter.OnClickListener{
//                    note -> openSingleNote(note)
//            })
//        })
    }

    fun openSingleNote(note: Note){
        if(note.Type == FileType.FILE.toString()){
            val intent = Intent(applicationContext, SingleNoteActivity::class.java)
            intent.putExtra("noteId", note.id)
            startActivity(intent)
        }
    }

    fun checkNoteData(){
        var intent = intent

        noteId = intent.getIntExtra("noteId", 0)

        if (noteId !=0){
            //readMode = true
            Log.i("XXXXXX", "Getting single not data!!....")
            viewModel.getSingle(noteId)
        }

    }

}