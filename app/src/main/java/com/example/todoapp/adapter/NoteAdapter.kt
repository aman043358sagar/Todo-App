package com.example.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.LayoutNoteBinding
import com.example.todoapp.model.Note
import kotlin.random.Random

class NoteAdapter(var noteList: ArrayList<Note>, val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    inner class ViewHolder(private val binding: LayoutNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val note = noteList[position]
            binding.cardView.setCardBackgroundColor(
                binding.root.resources.getColor(
                    note.color,
                    null
                )
            )

            binding.cardView.setOnClickListener{
                onItemClickListener.onItemClick(it, noteList[position])
            }

            binding.tvTitle.text = note.title
            binding.tvTitleDescription.text = note.titleDescription
            binding.tvTime.text = note.time
        }
    }

    interface OnItemClickListener{
        fun onItemClick(view: View, note: Note?)
    }
}