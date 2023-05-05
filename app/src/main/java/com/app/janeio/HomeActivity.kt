package com.app.janeio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View.GONE
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.janeio.model.Note
import com.app.janeio.ui.home.HomeFragment
import com.app.janeio.utils.NotesRecyclerAdapter
import com.app.janeio.view_models.NotesViewModel
import com.app.janeio.view_models.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.log

class HomeActivity:AppCompatActivity(){
    private lateinit var notesRecycler:RecyclerView
    private var viewManager = LinearLayoutManager(this)
    private lateinit var viewModel:NotesViewModel


    // floating fab
    lateinit var createNewBtn: ExtendedFloatingActionButton
    lateinit var createNoteBtn: FloatingActionButton
    lateinit var createFolderBtn: FloatingActionButton
    lateinit var createNoteBtnText: TextView
    lateinit var createFolderBtnText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // set action bar
        setContentView(R.layout.home_screen)

        // setup fragment

        loadFragment(NotesFragment())
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setOnNavigationItemSelectedListener {
            Log.i("XXXXXXXXX",it.itemId.toString())
            when(it.itemId){

                R.id.note_nav->{

                    loadFragment(NotesFragment())
                    true
                }
                R.id.todo_nav->{
                    loadFragment(TodoFragment())
                    true
                }
                else -> {
                    true
                }
            }
        }


        // floating feb
        createNewBtn =  findViewById<ExtendedFloatingActionButton>(R.id.create_new_btn2)
        createNewBtn.shrink()
        createFolderBtn = findViewById<FloatingActionButton>(R.id.create_folder_btn2)
        createNoteBtn = findViewById<FloatingActionButton>(R.id.create_note_btn2)
//        createFolderBtnText = view.findViewById<TextView>(R.id.create_folder_btn_text)
//        createNoteBtnText = view.findViewById<TextView>(R.id.create_note_btn_text)

        // create new note listener
        createNoteBtn.bringToFront()
        createNoteBtn.setOnClickListener{
            Log.i("XXXX", "Note listener")
            val intent = Intent(applicationContext, SingleNoteActivity::class.java)
            startActivity(intent)
        }

        // create folder on click lisftener
        createFolderBtn.setOnClickListener {
            val intent = Intent(applicationContext, NewFolderActivity::class.java)
            startActivity(intent)
        }



        // set buttons as invisible
        createFolderBtn.visibility = GONE
        createNoteBtn.visibility = GONE
//        createFolderBtnText.visibility = View.GONE
//        createNoteBtnText.visibility = View.GONE

        var isAllFabsVisible = false

        createNewBtn.setOnClickListener{
            Log.i("XXXXXXX", "expand")
            if(isAllFabsVisible){
                createFolderBtn.hide()
//                createFolderBtnText.visibility = View.GONE
                createNoteBtn.hide()
//                createNoteBtnText.visibility = View.GONE
                createNewBtn.shrink()
                isAllFabsVisible = false
            }else{


                createFolderBtn.show()
//                createFolderBtnText.visibility = View.VISIBLE
                createNoteBtn.show()
//                createNoteBtnText.visibility = View.VISIBLE
                createNewBtn.extend()
                isAllFabsVisible = true
            }
        }





//        notesRecycler = findViewById(R.id.notes_recycler)
        //val application = requireNotNull(this).application
        //val factory = ViewModelFactory()
        // view model

//        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
//        initailizeAdapter()
//        populateData()


        // create new note onclick open Single note page


    }
    private fun initailizeAdapter(){
        notesRecycler.layoutManager = viewManager
        observeData()
    }

    fun observeData(){
        viewModel.notesList.observe(this, {
            Log.i("data", it.toString())
            notesRecycler.adapter = NotesRecyclerAdapter(viewModel, it as ArrayList<Note>, this, NotesRecyclerAdapter.OnClickListener{
                note ->  
            })
        })
    }


    fun populateData(){
//        val note = Note(1, "Hello boy", "thisi i s note 1", "2:29","8:90")
//        var note2 = Note(1, "Stranger hi today", "thisi i s note 2 from stranger", "2:29","8:90")
//        viewModel.add(note)
//        viewModel.add(note2)
        notesRecycler.adapter?.notifyDataSetChanged()
    }


    private fun loadFragment(fragment:Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}
