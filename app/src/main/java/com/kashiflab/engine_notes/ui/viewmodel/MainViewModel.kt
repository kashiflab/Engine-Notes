package com.kashiflab.engine_notes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kashiflab.engine_notes.data.models.Notes
import com.kashiflab.engine_notes.data.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: NotesRepository): ViewModel() {

    val allNotes : LiveData<List<Notes>>
    get() = repo.allNotes

    init {
        getAllNotes()
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
}