package com.example.todoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddOrEditBinding
import kotlin.random.Random

class AddOrEditFragment : Fragment() {

    lateinit var binding: FragmentAddOrEditBinding
    private val args : AddOrEditFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddOrEditBinding.inflate(inflater,container,false)

        args.note?.let { note ->
            binding.etTitle.setText(note.title)
            binding.etTitleDescription.setText(note.titleDescription)
        }

        binding.btnBack.setOnClickListener{
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