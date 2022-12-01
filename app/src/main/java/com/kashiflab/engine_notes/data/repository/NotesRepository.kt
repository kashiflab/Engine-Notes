package com.kashiflab.engine_notes.data.repository

import com.kashiflab.engine_notes.data.models.Notes

interface NotesRepository {
    suspend fun getNotes()
    suspend fun updateNote(note: Notes)
    suspend fun insertNote(notes: Notes)
    suspend fun deleteNote(note: Notes)
    suspend fun pinNote(note: Notes)
    suspend fun unPinNote(note: Notes)
    suspend fun getNotesByCategory(categoryId: Int)
}