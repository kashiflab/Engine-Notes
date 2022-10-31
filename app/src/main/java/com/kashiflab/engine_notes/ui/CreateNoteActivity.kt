package com.kashiflab.engine_notes.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kashiflab.engine_notes.data.models.Notes
import com.kashiflab.engine_notes.data.utils.AppUtils
import com.kashiflab.engine_notes.databinding.ActivityCreateNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_create_note.*

@AndroidEntryPoint
class CreateNoteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateNoteBinding

    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        saveNote.setOnClickListener {
            insertNote(noteTitle.text.trim().toString(), noteDesc.text.trim().toString(), AppUtils.getDateTime(), "Me")
        }
    }

    private fun insertNote(title: String, desc: String , createdOn: String, createdBy: String){
        val note = Notes(id= 0,title=title, desc = desc, createdOn = createdOn, createdBy = createdBy)
        mainViewModel.insertNote(note)

        Toast.makeText(this@CreateNoteActivity, "Saved", Toast.LENGTH_SHORT).show()
        onBackPressedDispatcher.onBackPressed()
    }
}