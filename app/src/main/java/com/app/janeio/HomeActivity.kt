package com.app.janeio

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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
import kotlin.math.log

class HomeActivity:AppCompatActivity(){
    private lateinit var notesRecycler:RecyclerView
    private var viewManager = LinearLayoutManager(this)
    private lateinit var viewModel:NotesViewModel
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


//        notesRecycler = findViewById(R.id.notes_recycler)
//        val application = requireNotNull(this).application
//        val factory = ViewModelFactory()
//        // view model
//
//        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
//        initailizeAdapter()
//        populateData()

    }
    private fun initailizeAdapter(){
        notesRecycler.layoutManager = viewManager
        observeData()
    }

    fun observeData(){
        viewModel.notesList.observe(this, {
            Log.i("data", it.toString())
            notesRecycler.adapter = NotesRecyclerAdapter(viewModel, it, this)
        })
    }


    fun populateData(){
        val note = Note(1, "Hello boy", "thisi i s note 1", "2:29","8:90")
        var note2 = Note(1, "Stranger hi today", "thisi i s note 2 from stranger", "2:29","8:90")
        viewModel.add(note)
        viewModel.add(note2)
        notesRecycler.adapter?.notifyDataSetChanged()
    }


    private fun loadFragment(fragment:Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}
