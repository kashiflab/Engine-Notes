package com.kashiflab.engine_notes.di

import android.content.Context
import androidx.room.Room
import com.kashiflab.engine_notes.db.NotesDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesNotesDB(@ApplicationContext context: Context) : NotesDB{
        return Room.databaseBuilder(context, NotesDB::class.java, "notes").build()
    }
}