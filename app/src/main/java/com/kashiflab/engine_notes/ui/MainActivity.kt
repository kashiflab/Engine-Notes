package com.kashiflab.engine_notes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kashiflab.engine_notes.data.models.Notes
import com.kashiflab.engine_notes.databinding.ActivityMainBinding
import com.kashiflab.engine_notes.ui.adapter.NotesAdapter
import com.kashiflab.engine_notes.ui.adapter.OnNoteClickListener
import com.kashiflab.engine_notes.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnNoteClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainViewModel: MainViewModel

    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        adapter = NotesAdapter(emptyList(), this)

        noteRV.layoutManager = LinearLayoutManager(this)
        noteRV.setHasFixedSize(true)
        noteRV.adapter = adapter

        mainViewModel.allNotes.observe(this, Observer {
            if(it.isNotEmpty()){
                adapter.setNotesList(it)
            }
        })

        addNoteBtn.setOnClickListener {
            startActivity(Intent(this, CreateNoteActivity::class.java))
        }

    }

    override fun onNoteClick(note: Notes) {
        startActivity(Intent(this, CreateNoteActivity::class.java).putExtra("note", note))
    }
}