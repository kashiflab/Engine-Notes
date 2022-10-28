package com.kashiflab.engine_notes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kashiflab.engine_notes.data.models.Notes
import com.kashiflab.engine_notes.db.dao.NotesDao

@Database(entities = [Notes::class], version = 1)
abstract class NotesDB: RoomDatabase() {
    abstract fun noteDao() : NotesDao
}