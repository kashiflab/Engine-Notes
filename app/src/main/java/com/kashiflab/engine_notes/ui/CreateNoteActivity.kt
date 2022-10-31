package com.kashiflab.engine_notes.ui

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kashiflab.engine_notes.data.models.Notes
import com.kashiflab.engine_notes.data.utils.AppUtils
import com.kashiflab.engine_notes.databinding.ActivityCreateNoteBinding
import com.kashiflab.engine_notes.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_create_note.*

@AndroidEntryPoint
class CreateNoteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateNoteBinding

    private val mainViewModel : MainViewModel by viewModels()

    private lateinit var note : Notes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPendingIntent()

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        saveNote.setOnClickListener {
            if(note!=null){
                note.modifiedBy = AppUtils.getDateTime()
                note.modifiedOn = "Me"
                note.title = noteTitle.text.trim().toString()
                note.desc = noteDesc.text.trim().toString()
                updateNote(note)
            }else{
                insertNote(noteTitle.text.trim().toString(), noteDesc.text.trim().toString(), AppUtils.getDateTime(), "Me")
            }

        }
    }

    private fun updateNote(note: Notes){
        mainViewModel.updateNote(note)

        Toast.makeText(this@CreateNoteActivity, "Updated", Toast.LENGTH_SHORT).show()
        onBackPressedDispatcher.onBackPressed()
    }

    private fun insertNote(title: String, desc: String , createdOn: String, createdBy: String){
        val note = Notes(id= 0,title=title, desc = desc, createdOn = createdOn, createdBy = createdBy)
        mainViewModel.insertNote(note)

        Toast.makeText(this@CreateNoteActivity, "Saved", Toast.LENGTH_SHORT).show()
        onBackPressedDispatcher.onBackPressed()
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    private fun getPendingIntent(){
        if(intent.hasExtra("note")){
            note = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra("note", Notes::class.java)!!
            }else{
                intent.getSerializableExtra("note") as Notes
            }
            setData()
        }

    }
    private fun setData(){
        noteTitle.setText(note.title)
        noteDesc.setText(note.desc)
    }

}