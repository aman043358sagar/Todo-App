package com.example.todoapp.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todoapp.NoteApplication
import com.example.todoapp.R
import com.example.todoapp.adapter.NoteAdapter
import com.example.todoapp.customView.CustomEditText
import com.example.todoapp.databinding.FragmentNotesBinding
import com.example.todoapp.room.entity.Note
import com.example.todoapp.room.viewModel.NoteViewModel
import com.example.todoapp.room.viewModel.NoteViewModelFactory


class NotesFragment : Fragment(), NoteAdapter.OnItemClickListener {
    lateinit var binding: FragmentNotesBinding
    lateinit var adapter: NoteAdapter
    lateinit var etSearch: CustomEditText
    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((activity?.application as NoteApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        etSearch = binding.etSearch
        loadRecyclerView()
        binding.btnAdd.setOnClickListener {
            this.onItemClick(it, null)
        }

        noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            notes?.let {
                adapter.setData(it)
            }
        }

        etSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                adapter.filter.filter(textView.text.toString())
                etSearch.hideKeyboard()
                return@setOnEditorActionListener true
            }
            false
        }

        etSearch.setOnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2
            if (event.action === MotionEvent.ACTION_UP && etSearch.compoundDrawables[DRAWABLE_RIGHT] != null) {
                if (event.rawX >= etSearch.right - etSearch.compoundDrawables[DRAWABLE_RIGHT].bounds.width()
                ) {
                    if (etSearch.hasFocus()) {
                        etSearch.clearFocus()
                    }
                    etSearch.setText("")
                    adapter.filter.filter("")
                    etSearch.hideKeyboard()
                    return@setOnTouchListener true
                }
            }
            false
        }

        binding.etSearch.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                binding.btnAdd.visibility = GONE
                binding.etSearch.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_search,
                    0,
                    R.drawable.ic_cancel,
                    0
                )
            } else {
                binding.btnAdd.visibility = VISIBLE
                binding.etSearch.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_search,
                    0,
                    0,
                    0
                )
            }
        }
        return binding.root
    }

    private fun loadRecyclerView() {

        adapter = NoteAdapter(this)
        binding.recyclerView.adapter = adapter
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = staggeredGridLayoutManager
    }

    override fun onItemClick(view: View, note: Note?) {
        view.findNavController().navigate(
            NotesFragmentDirections.actionNotesFragmentToAddOrEditFragment(note)
        )
    }

    override fun onItemLongClick(view: View, note: Note?) {
        note?.let {
            showDeleteDialog(it)
        }

    }

    private fun showDeleteDialog(note: Note) {
        val alertDialog: AlertDialog = AlertDialog.Builder(context).create()

        alertDialog.setTitle("Are you sure want to delete ?")

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Delete") { dialogInterface, which ->
            noteViewModel.delete(note)
        }

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel") { dialogInterface, which ->
            alertDialog.dismiss()
        }

        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}