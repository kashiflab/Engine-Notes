package com.kashiflab.engine_notes.db.dao

import androidx.room.*
import com.kashiflab.engine_notes.data.models.Notes

@Dao
interface NotesDao {

    @Insert
    suspend fun insertNote(note: Notes)

    @Query("SELECT * FROM notes_table ORDER BY createdOn ASC")
    suspend fun getAllNotes() : List<Notes>

    @Query("SELECT * FROM notes_table WHERE categoryId=:catId")
    suspend fun getNotesByCategoryId(catId: Int) : List<Notes>

    @Update
    suspend fun updateNote(note: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)
}