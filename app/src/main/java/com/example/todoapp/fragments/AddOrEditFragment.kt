package com.example.todoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.NoteApplication
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddOrEditBinding
import com.example.todoapp.room.entity.Note
import com.example.todoapp.room.viewModel.NoteViewModel
import com.example.todoapp.room.viewModel.NoteViewModelFactory
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class AddOrEditFragment : Fragment() {

    lateinit var binding: FragmentAddOrEditBinding
    private val args: AddOrEditFragmentArgs by navArgs()
    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((activity?.application as NoteApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddOrEditBinding.inflate(inflater, container, false)
        val note = args.note
        if (note == null) {
            binding.btnDone.setOnClickListener {
                val stamp = Timestamp(System.currentTimeMillis()) // from java.sql.timestamp
                val date = Date(stamp.time)

                val sdf = SimpleDateFormat("dd MMM yyyy hh:mm a")

                sdf.timeZone = TimeZone.getTimeZone("Asia/Kolkata")

                val formattedDateTime = sdf.format(date)
                val n = Note(
                    title = binding.etTitle.text.toString(),
                    titleDescription = binding.etTitleDescription.text.toString(),
                    time = formattedDateTime,
                    color = randomColor()
                )
                noteViewModel.insert(n)
                it.findNavController().popBackStack()
                Toast.makeText(context, "insert", Toast.LENGTH_SHORT).show()
            }
        }else{
            binding.etTitle.setText(note.title)
            binding.etTitleDescription.setText(note.titleDescription)
            binding.btnDone.setOnClickListener {
                note.title = binding.etTitle.text.toString()
                note.titleDescription = binding.etTitleDescription.text.toString()
                noteViewModel.update(note)
                it.findNavController().popBackStack()
            }
        }

        binding.btnBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        return binding.root
    }

    private fun randomColor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.card)
        list.add(R.color.card1)
        list.add(R.color.card2)
        list.add(R.color.card3)
        list.add(R.color.card4)
        list.add(R.color.card5)
        list.add(R.color.card6)
        list.add(R.color.card7)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

}