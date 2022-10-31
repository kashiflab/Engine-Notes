package com.kashiflab.engine_notes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kashiflab.engine_notes.data.models.Notes
import com.kashiflab.engine_notes.db.NotesDB
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(private val notesDB: NotesDB) {

    private val _allNotes = MutableLiveData<List<Notes>>()
    val allNotes : LiveData<List<Notes>>
    get() = _allNotes

    suspend fun getNotes() {
        val notes = notesDB.noteDao().getAllNotes()
        _allNotes.postValue(notes)
    }

    suspend fun updateNote(note: Notes){
        notesDB.noteDao().updateNote(note)
        getNotes()
    }

    suspend fun insertNote(notes: Notes){
        notesDB.noteDao().insertNote(notes)
        getNotes()
    }
}