package com.app.janeio

import Janeio.R
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.app.janeio.model.FileType
import com.app.janeio.model.Folder
import com.app.janeio.model.Note
import com.app.janeio.view_models.FolderViewModel
import com.app.janeio.view_models.NotesViewModel
import com.app.janeio.view_models.ViewModelFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class NewFolderActivity : AppCompatActivity() {
    lateinit var backBtn: ImageButton
    lateinit var toolbar:Toolbar
    lateinit var folderNameInput:EditText
    lateinit var btnSaveFolder:Button
    lateinit var notesViewModel: NotesViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_folder)


        // setup toolbar from the UI file
        toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.back_toolbar)
        setSupportActionBar(toolbar)

        // link UI data
        folderNameInput = findViewById(R.id.folder_name_input)
        btnSaveFolder= findViewById(R.id.btn_save_folder)

        // onclick listeners
        btnSaveFolder.setOnClickListener{
            save()
            finish()
        }

        // view moddel setup
        val factory=ViewModelFactory(application)
        notesViewModel = factory.create(NotesViewModel::class.java)
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
                // Log.i("XXXXXXX","HHHHHHHHH")
                finish()
            }
        }
        return true
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun save(){
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formattedDateTime = currentDateTime.format(formatter)
        val folder = Note(
            null,
            folderNameInput.text.toString(),
            "",
            FileType.FOLDER.toString(),
            formattedDateTime,
            formattedDateTime,
            null
        )
        notesViewModel.add(folder)

    }
}