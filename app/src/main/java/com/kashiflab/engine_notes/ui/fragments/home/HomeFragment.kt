package com.kashiflab.engine_notes.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kashiflab.engine_notes.data.models.Notes
import com.kashiflab.engine_notes.databinding.FragmentHomeBinding
import com.kashiflab.engine_notes.ui.CreateNoteActivity
import com.kashiflab.engine_notes.ui.adapter.NotesAdapter
import com.kashiflab.engine_notes.ui.adapter.OnItemClickListener
import com.kashiflab.engine_notes.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnItemClickListener<Notes> {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var adapter: NotesAdapter

    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        adapter = NotesAdapter(emptyList(), this)

        binding.noteRV.layoutManager = LinearLayoutManager(context)
        binding.noteRV.setHasFixedSize(true)
        binding.noteRV.adapter = adapter

        mainViewModel.allNotes.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                adapter.setNotesList(it)
            }
        })

        binding.addNoteBtn.setOnClickListener {
            startActivity(Intent(context, CreateNoteActivity::class.java))
        }

        return binding.root
    }

    override fun onNoteClick(data: Notes) {
        startActivity(Intent(context, CreateNoteActivity::class.java).putExtra("note", data))
    }

}
