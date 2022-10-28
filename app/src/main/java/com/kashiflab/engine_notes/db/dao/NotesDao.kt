package com.kashiflab.engine_notes.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kashiflab.engine_notes.data.models.Notes

@Dao
interface NotesDao {

    @Insert
    suspend fun insertNote(note: Notes)

    @Query("SELECT * FROM notes_table")
    suspend fun getAllNotes() : List<Notes>
}