package com.kashiflab.engine_notes.data.repository

import com.kashiflab.engine_notes.data.models.Notes

interface NotesRepository {
    suspend fun getNotes()
    suspend fun updateNote(note: Notes)
    suspend fun insertNote(notes: Notes)
    suspend fun getNotesByCategory(categoryId: Int)
}