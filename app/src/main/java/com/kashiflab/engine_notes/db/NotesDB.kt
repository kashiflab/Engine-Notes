package com.kashiflab.engine_notes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kashiflab.engine_notes.data.models.Category
import com.kashiflab.engine_notes.data.models.Notes
import com.kashiflab.engine_notes.data.models.Tags
import com.kashiflab.engine_notes.db.dao.CategoryDao
import com.kashiflab.engine_notes.db.dao.NotesDao
import com.kashiflab.engine_notes.db.dao.TagsDao

@Database(entities = [
    Notes::class,
    Category::class,
    Tags::class,
                     ],
    version = 1)
@TypeConverters(Converters::class)
abstract class NotesDB: RoomDatabase() {
    abstract fun noteDao() : NotesDao
    abstract fun categoryDao() : CategoryDao
    abstract fun tagsDao() : TagsDao
}