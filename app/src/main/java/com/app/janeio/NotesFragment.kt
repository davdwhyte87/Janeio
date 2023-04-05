package com.app.janeio


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.janeio.model.Note
import com.app.janeio.utils.NotesRecyclerAdapter
import com.app.janeio.view_models.NotesViewModel
import com.app.janeio.view_models.ViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var notesRecycler:RecyclerView
    private var viewManager = LinearLayoutManager(this.context)

    private lateinit var viewModel: NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


//
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.notes_fragment, container, false)
        notesRecycler = view.findViewById(R.id.notes_recycler)
        val application = requireNotNull(this.context).applicationContext
        val factory = ViewModelFactory()
//        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        viewModel = factory.create(NotesViewModel::class.java)
        initailizeAdapter()
        populateData()
        return view

    }

    private fun initailizeAdapter(){
        notesRecycler.layoutManager = viewManager
        observeData()
    }

    fun observeData(){
        viewModel.notesList.observe(this.viewLifecycleOwner, {
            Log.i("data", it.toString())
            notesRecycler.adapter = NotesRecyclerAdapter(viewModel, it,
                this.requireContext()!!.applicationContext!!
            )
        })
    }

    fun populateData(){
        val note = Note(1, "Hello boy", "thisi i s note 1", "2:29","8:90")
        var note2 = Note(1, "Stranger hi today", "thisi i s note 2 from stranger", "2:29","8:90")
        viewModel.add(note)
        viewModel.add(note2)
        notesRecycler.adapter?.notifyDataSetChanged()
    }

    fun setClicked(){

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
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}