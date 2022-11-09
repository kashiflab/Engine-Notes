package com.kashiflab.engine_notes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kashiflab.engine_notes.R
import com.kashiflab.engine_notes.data.models.Category
import com.kashiflab.engine_notes.data.models.Notes
import com.kashiflab.engine_notes.data.repository.MainRepository
import com.kashiflab.engine_notes.data.utils.AppUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: MainRepository): ViewModel() {

    val allNotes : LiveData<List<Notes>>
    get() = repo.allNotes

    val allCategories: LiveData<List<Category>>
    get() = repo.allCategories

    val tagsName: LiveData<List<String>>
    get() = repo.tagNames

    private val categories : MutableList<Category> = arrayListOf(
        Category(1,"Work Notes", R.drawable.work_notes, AppUtils.getDateTime(),"Admin","",""),
        Category(2,"Random Thoughts", R.drawable.speech_bubble, AppUtils.getDateTime(),"Admin","",""),
        Category(3,"Messages",R.drawable.chat, AppUtils.getDateTime(),"Admin","",""),
        Category(5,"Recordings",R.drawable.radio_waves, AppUtils.getDateTime(),"Admin","","")
    )

    init {
        insertCategory()
        getCategories()
        getAllNotes()
    }

    private fun insertCategory(){
        viewModelScope.launch {
            categories.forEach {
                repo.insertCategory(it)
            }
        }
    }

    fun updateNote(note: Notes){
        viewModelScope.launch {
            repo.updateNote(note)
        }
    }

    fun insertNote(note: Notes){
        viewModelScope.launch {
            repo.insertNote(note)
        }
    }

    private fun getAllNotes(){
        viewModelScope.launch {
            repo.getNotes()
        }
    }

    fun getCategoryId(categoryName: String) : Int {
        return repo.getCategoryId(categoryName)
    }

    fun getTagsName(tagsId: ArrayList<Int>) {
        viewModelScope.launch {
            repo.getTagsName(tagsId)
        }
    }

    private fun getCategories(){
        viewModelScope.launch {
            repo.getAllCategories()
        }
    }
}