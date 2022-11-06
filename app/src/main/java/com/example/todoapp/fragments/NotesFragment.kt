package com.example.todoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapter.NoteAdapter
import com.example.todoapp.customView.CustomEditText
import com.example.todoapp.databinding.FragmentNotesBinding
import com.example.todoapp.model.Note


class NotesFragment : Fragment(), NoteAdapter.OnItemClickListener {
    lateinit var binding: FragmentNotesBinding
    lateinit var adapter: NoteAdapter
    lateinit var noteList: ArrayList<Note>
    lateinit var etSearch: CustomEditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        etSearch = binding.etSearch
        loadRecyclerView()
        binding.btnAdd.setOnClickListener {
            this.onItemClick(it, null)
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
        noteList = arrayListOf(
            Note(
                title = "Lorem Ipsum",
                titleDescription = getString(R.string.lorem),
                time = "12 July 2023 11:43 PM",
                color = R.color.card
            ),
            Note(
                title = "Cake",
                titleDescription = "1. Sugar\n2. Milk\n3. Egg",
                time = "12 July 2023 11:43 PM",
                color = R.color.card2
            )
        )

        adapter = NoteAdapter(noteList, this)
        binding.recyclerView.adapter = adapter
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = staggeredGridLayoutManager
    }

    override fun onItemClick(view: View, note: Note?) {
        view.findNavController().navigate(
            NotesFragmentDirections.actionNotesFragmentToAddOrEditFragment(note)
        )
    }

}