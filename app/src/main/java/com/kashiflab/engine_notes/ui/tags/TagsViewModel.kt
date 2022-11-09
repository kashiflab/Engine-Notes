package com.kashiflab.engine_notes.ui.tags

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kashiflab.engine_notes.data.models.Tags
import com.kashiflab.engine_notes.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagsViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {

    val allTags : LiveData<List<Tags>>
    get() = repo.allTags

    init {
        getAllTags()
    }

    private fun getAllTags(){
        viewModelScope.launch {
            repo.getAllTags()
        }
    }

    fun insertTag(tag: Tags){
        viewModelScope.launch {
            repo.insertTag(tag)
        }
    }

    fun updateTag(tag: Tags){
        viewModelScope.launch {
            repo.updateTag(tag)
        }
    }

    fun deleteTag(tag: Tags){
        viewModelScope.launch {
            repo.deleteTag(tag)
        }
    }
}