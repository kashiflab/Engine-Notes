package com.kashiflab.engine_notes.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.kashiflab.engine_notes.ui.tags.TagsActivity
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

    private var tagsId : ArrayList<Int> = ArrayList()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101 && resultCode == RESULT_OK){
            tagsId.clear()
            data?.getIntegerArrayListExtra("tagsId")?.let { tagsId.addAll(it) }
            Log.i("NoteActivity123",tagsId.size.toString())

            mainViewModel.getTagsName(tagsId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPendingIntent()

        adapter = CategoryAdapter(emptyList(), this, true)

        mainViewModel.allCategories.observe(this, Observer { it ->
            if(it!=null && it.isNotEmpty()){
                var catName = it[0].categoryName
                var catIcon = it[0].categoryIcon
                if(note!=null){
                    catName =
                        it.firstOrNull { it1-> it1.id == note!!.categoryId }?.categoryName ?: it[0].categoryName
                    catIcon =
                        it.firstOrNull { it1-> it1.id == note!!.categoryId }?.categoryIcon ?: it[0].categoryIcon

                }
                categoryTitle.text = catName
                categoryIcon.setBackgroundResource(catIcon)
                adapter.setCategoriesList(it)
            }

        })

        mainViewModel.tagsName.observe(this, Observer {
            var str  = ""
            it.forEach { a ->
                str += "$a\t"
            }
            labelsTv.text = str
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

        addLabels.setOnClickListener {
            val list = if(note!=null){
                note?.tag_id as ArrayList<Int>
            }else {
                tagsId
            }

            startActivityForResult(Intent(this, TagsActivity::class.java)
                .putIntegerArrayListExtra("tagsId",list), 101)
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialog)
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
            createdBy = createdBy, categoryId = categoryId, tag_id = tagsId)
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
        tagsId = note?.tag_id as ArrayList<Int>
        categoryId = note?.categoryId!!

        mainViewModel.getTagsName(tagsId)
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