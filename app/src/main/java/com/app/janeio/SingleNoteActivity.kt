package com.app.janeio

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.lifecycleScope
import com.app.janeio.model.FileType
import com.app.janeio.model.Note
import com.app.janeio.view_models.NotesViewModel
import com.app.janeio.view_models.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class SingleNoteActivity : AppCompatActivity() {
    private lateinit var toolbar:androidx.appcompat.widget.Toolbar
    private lateinit var viewModel: NotesViewModel

    private lateinit var noteBody: EditText
    private lateinit var noteHead:EditText
    private lateinit var dateTime: TextView

    var readMode = false
    private lateinit var backBtn:ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_note)

        // setup toolbar from the UI file
        toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.back_toolbar)
        setSupportActionBar(toolbar)


        // set note body and head
        noteBody = findViewById(R.id.note_body)
        noteHead = findViewById(R.id.note_head)
        dateTime = findViewById(R.id.date_time)

        // setup view models
        val factory = ViewModelFactory(application)
        viewModel = factory.create(NotesViewModel::class.java)


        observerData()
        //check if there is a note data to display
        checkNoteData()

    }

    fun checkNoteData(){
        var intent = intent

        val noteId = intent.getIntExtra("noteId", 0)

        if (noteId !=0){
            readMode = true
            viewModel.getSingle(noteId)
            Log.i("XXXXX", "Found Note ID "+noteId.toString())
//
//            noteHead.setText(viewModel.singleNote.value?.Title ?: "")
//            noteBody.setText(viewModel.singleNote.value?.Note ?: "")
//            dateTime.setText(viewModel.singleNote.value?.CreatedAt ?: "")
        }

    }

    fun observerData(){
//        viewModel.singleNote.observe(this, {
//            Log.i("XXXXXX", "Data set "+it.Note)
//            noteHead.setText(it.Title)
//            noteBody.setText(it.Note)
//            dateTime.setText(it.CreatedAt)
//        })

        lifecycleScope.launchWhenStarted {
            viewModel.singleNote.collectLatest {
                Log.i("XXXXXX", "Data set "+it.Note)
                noteHead.setText(it.Title)
                noteBody.setText(it.Note)
                dateTime.setText(it.CreatedAt)
            }
        }
    }


    // add data to the view model
    @RequiresApi(Build.VERSION_CODES.O)
    fun addData(){
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formattedDateTime = currentDateTime.format(formatter)
        val note = Note(null,
            noteHead.text.toString(),
            noteBody.text.toString(),
            FileType.FILE.toString(),
            formattedDateTime,
            formattedDateTime,
            null
        )
        viewModel.add(note)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroy() {
        super.onDestroy()
        // save data before activity closes
        if (noteBody.text.toString() != "" && readMode==false){
            addData()
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.back_btn->{
                finish()
            }
        }
        return false
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

//        menuInflater.inflate(R.menu.back_menu, menu)
//        return super.onCreateOptionsMenu(menu)
        val actionBar = supportActionBar
        if (actionBar != null) {
            Log.i("XXXX", "aactionbae")
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
        return true
    }
}