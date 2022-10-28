package com.kashiflab.engine_notes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kashiflab.engine_notes.R
import com.kashiflab.engine_notes.data.models.Notes
import com.kashiflab.engine_notes.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.allNotes.observe(this, Observer {
            if(it.isNotEmpty()){
                hello.text = it[0].title
            }
        })

        insertNote()

    }

    private fun insertNote(){
        val note = Notes(id= 0,title="Hello", desc = "notification description", createdOn = "1", createdBy = "1")

        mainViewModel.insertNote(note)
    }
}