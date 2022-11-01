package com.kashiflab.engine_notes.ui

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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

    private var note : Notes? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPendingIntent()

        backBtn.setOnClickListener {
            addUpdateNotes()
            onBackPressedDispatcher.onBackPressed()
        }

        saveNote.setOnClickListener {
            addUpdateNotes()
        }
    }

    private fun addUpdateNotes(){
        if(note!=null){
            note?.modifiedBy = AppUtils.getDateTime()
            note?.modifiedOn = "Me"
            note?.title = noteTitle.text.trim().toString()
            note?.desc = noteDesc.text.trim().toString()
            updateNote(note!!)
        }else if(noteDesc.text.trim().toString().isNotEmpty()){
            insertNote(noteTitle.text.trim().toString(), noteDesc.text.trim().toString(), AppUtils.getDateTime(), "Me")
        }else{
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun updateNote(note: Notes){
        mainViewModel.updateNote(note)

        onBackPressedDispatcher.onBackPressed()
    }

    private fun insertNote(title: String, desc: String , createdOn: String, createdBy: String){
        val note = Notes(id= 0,title=title, desc = desc, createdOn = createdOn, createdBy = createdBy)
        mainViewModel.insertNote(note)

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
        noteTitle.setText(note?.title)
        noteDesc.setText(note?.desc)
    }

    override fun onBackPressed() {
        addUpdateNotes()
        super.getOnBackPressedDispatcher().onBackPressed()
    }

}