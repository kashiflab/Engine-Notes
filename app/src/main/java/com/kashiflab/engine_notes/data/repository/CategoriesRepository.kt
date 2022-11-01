package com.kashiflab.engine_notes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kashiflab.engine_notes.data.models.Category
import com.kashiflab.engine_notes.db.NotesDB
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesRepository @Inject constructor(private val notesDB: NotesDB) {
    private val _allCategories = MutableLiveData<List<Category>>()
    val allCategories: LiveData<List<Category>>
    get() = _allCategories

    suspend fun getAllCategories(){
        val categories = notesDB.categoryDao().getAllCategories()
        _allCategories.postValue(categories)
    }

    suspend fun insertCategory(category: Category){
        notesDB.categoryDao().insertCategory(category)
        getAllCategories()
    }

    suspend fun updateCategory(category: Category){
        notesDB.categoryDao().updateCategory(category)
        getAllCategories()
    }
}