package com.app.janeio


import Janeio.R
import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.janeio.database.NoteRepository
import com.app.janeio.model.FileType
import com.app.janeio.model.Note
import com.app.janeio.utils.NotesRecyclerAdapter
import com.app.janeio.view_models.NotesViewModel

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotesFragment @Inject constructor(val xapplication: Application, val noteRepository: NoteRepository) : Fragment(), View.OnTouchListener, ViewTreeObserver.OnScrollChangedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var notesRecycler:RecyclerView
    private var viewManager = LinearLayoutManager(this.context)
    val notesViewModel:NotesViewModel by viewModels()
    lateinit var searchText:EditText
    private lateinit var viewModel: NotesViewModel

    // floating fab
    lateinit var createNewBtn:ExtendedFloatingActionButton
    lateinit var createNoteBtn:FloatingActionButton
    lateinit var createFolderBtn:FloatingActionButton
    lateinit var createNoteBtnText: TextView
    lateinit var createFolderBtnText:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

//
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllNotes()
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.home_fragment2, container, false)
        notesRecycler = view.findViewById(R.id.notes_recycler)
        val application = requireNotNull(this.context).applicationContext
//        val factory = ViewModelFactory(xapplication, noteRepository)
//        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
//        viewModel = factory.create(NotesViewModel::class.java)

        initailizeAdapter()
        populateData()


        searchText = view.findViewById<EditText>(R.id.input_search)
//        val targetScrollView = view.findViewById<NestedScrollView>(R.id.home_scroll_view)
//
//
//        targetScrollView.setOnTouchListener(this)
//        targetScrollView.viewTreeObserver.addOnScrollChangedListener(this)
//        view.findViewById<Button>(R.id.add_btn).setOnClickListener{
//           addData()
//        }


        // floating feb
//        createNewBtn =  view.findViewById<ExtendedFloatingActionButton>(R.id.create_new_btn)
//        createNewBtn.shrink()
//        createFolderBtn = view.findViewById<FloatingActionButton>(R.id.create_folder_btn)
//        createNoteBtn = view.findViewById<FloatingActionButton>(R.id.create_note_btn)
//        createFolderBtnText = view.findViewById<TextView>(R.id.create_folder_btn_text)
//        createNoteBtnText = view.findViewById<TextView>(R.id.create_note_btn_text)
//        // set buttons as invisible
//        createFolderBtn.visibility = View.GONE
//        createNoteBtn.visibility = View.GONE
//        createFolderBtnText.visibility = View.GONE
//        createNoteBtnText.visibility = View.GONE
//
//        var isAllFabsVisible = false
//
//        createNewBtn.setOnClickListener{
////            Log.i("XXXXXXX", "expand")
//            if(isAllFabsVisible){
//                createFolderBtn.hide()
//                createFolderBtnText.visibility = View.GONE
//                createNoteBtn.hide()
//                createNoteBtnText.visibility = View.GONE
//                createNewBtn.shrink()
//                isAllFabsVisible = false
//            }else{
//
//
//                createFolderBtn.show()
//                createFolderBtnText.visibility = View.VISIBLE
//                createNoteBtn.show()
//                createNoteBtnText.visibility = View.VISIBLE
//                createNewBtn.extend()
//                isAllFabsVisible = true
//            }
//        }


//        targetScrollView.getViewTreeObserver().addOnScrollChangedListener(OnScrollChangedListener {
//
//            val scrollX: Int = targetScrollView.getScrollY()
//            if (scrollX > 565 ){
//                Log.d("TAG", "hit scool")
//
//
//                targetScrollView.setOnTouchListener(object: View.OnTouchListener{
//                    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                        return true
//                    }
//
//
//                })
//            }
//            Log.d("TAG", "scrollX: $scrollX")
//        })



        return view

    }

    private fun initailizeAdapter(){
        notesRecycler.layoutManager = viewManager
        viewModel.getAllNotes()
        observeData()

    }

//    fun observeData(){
//        viewModel.notesList.observe(this.viewLifecycleOwner, {
//            Log.i("data", it.toString())
//            notesRecycler.adapter = NotesRecyclerAdapter(viewModel, it.toTypedArray() ,
//                this.requireContext()!!.applicationContext!!, NotesRecyclerAdapter.OnClickListener{
//                    note ->  openSingleNote(note)
//                }
//            )
//        })
//    }

    fun observeData(){
        lifecycleScope.launch {
            viewModel.notesList.collectLatest {
                Log.i("data", it.toString())
                notesRecycler.adapter = NotesRecyclerAdapter(viewModel, it.toTypedArray() , requireContext().applicationContext, NotesRecyclerAdapter.OnClickListener{
                        note ->openSingleNote(note)
                })
            }
        }
    }

    fun openSingleNote(note: Note){
        if(note.Type == FileType.FILE.toString()){
            val intent = Intent(this.requireContext()!!.applicationContext, SingleNoteActivity::class.java)
            intent.putExtra("noteId", note.id)
            startActivity(intent)
        }
        else if (note.Type == FileType.FOLDER.toString()){
            val intent = Intent(this.requireContext()!!.applicationContext, SingleFolderActivity::class.java)
            intent.putExtra("noteId", note.id)
            startActivity(intent)
        }
    }

    fun populateData(){
//        val note = Note(1, "Hello boy", "thisi i s note 1", "2:29","8:90")
//        var note2 = Note(1, "Stranger hi today", "thisi i s note 2 from stranger", "2:29","8:90")
//        viewModel.add(note)
//        viewModel.add(note2)
        notesRecycler.adapter?.notifyDataSetChanged()
    }

    fun addData(){
//        val inputText = searchText.text
//        val note = Note(null, inputText.toString(), "thisi i s note 1", "2:29","8:90")
//        viewModel.add(note)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
//@JvmStatic
        //fun newInstance(param1: String, param2: String) =
//            NotesFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return true
    }

    override fun onScrollChanged() {

    }


}