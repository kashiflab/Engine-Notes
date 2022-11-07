package com.kashiflab.engine_notes.ui

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kashiflab.engine_notes.R
import com.kashiflab.engine_notes.data.models.Category
import com.kashiflab.engine_notes.data.models.Notes
import com.kashiflab.engine_notes.data.utils.AppUtils
import com.kashiflab.engine_notes.databinding.ActivityCreateNoteBinding
import com.kashiflab.engine_notes.ui.adapter.CategoryAdapter
import com.kashiflab.engine_notes.ui.adapter.OnItemClickListener
import com.kashiflab.engine_notes.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_create_note.*
import kotlinx.android.synthetic.main.bottom_sheet.*


@AndroidEntryPoint
class CreateNoteActivity : AppCompatActivity(), OnItemClickListener<Category> {

    private lateinit var binding : ActivityCreateNoteBinding

    private val mainViewModel : MainViewModel by viewModels()

    private lateinit var adapter: CategoryAdapter

    private var note : Notes? = null
    private var categoryId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPendingIntent()

        adapter = CategoryAdapter(emptyList(), this, true)

        mainViewModel.allCategories.observe(this, Observer {
            if(it!=null){
                adapter.setCategoriesList(it)
            }

        })

        backBtn.setOnClickListener {
            addUpdateNotes()
            onBackPressedDispatcher.onBackPressed()
        }

        saveNote.setOnClickListener {
            addUpdateNotes()
        }

        categoryTitle.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet)

        bottomSheetDialog.categoriesRVBT.layoutManager = LinearLayoutManager(this)
        bottomSheetDialog.categoriesRVBT.setHasFixedSize(true)
        bottomSheetDialog.categoriesRVBT.adapter = adapter

        bottomSheetDialog.show()
    }

    private fun addUpdateNotes(){
        val categoryId = mainViewModel.getCategoryId(categoryTitle.text.toString())
        if(categoryId==0){
            Toast.makeText(this,"Please select category", Toast.LENGTH_SHORT).show()
            return
        }
        if(note!=null){
            note?.modifiedOn = AppUtils.getDateTime()
            note?.modifiedBy = "Me"
            note?.title = noteTitle.text.trim().toString()
            note?.desc = noteDesc.text.trim().toString()
            note?.categoryId = mainViewModel.getCategoryId(categoryTitle.text.toString())
            updateNote(note!!)
        }else if(noteDesc.text.trim().toString().isNotEmpty()){
            insertNote(noteTitle.text.trim().toString(), noteDesc.text.trim().toString(),
                AppUtils.getDateTime(), "Me", categoryId)
        }else{
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun updateNote(note: Notes){
        mainViewModel.updateNote(note)

        onBackPressedDispatcher.onBackPressed()
    }

    private fun insertNote(title: String, desc: String , createdOn: String, createdBy: String, categoryId: Int){
        val note = Notes(id= 0,title=title, desc = desc, createdOn = createdOn,
            createdBy = createdBy, categoryId = categoryId)
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

    override fun onNoteClick(data: Category) {
        categoryId = data.id
        categoryTitle.text = data.categoryName
        categoryIcon.setBackgroundResource(data.categoryIcon)
    }

}