package com.kashiflab.engine_notes.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kashiflab.engine_notes.data.models.Category
import com.kashiflab.engine_notes.data.models.Notes
import com.kashiflab.engine_notes.data.models.Tags
import com.kashiflab.engine_notes.db.NotesDB
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val notesDB: NotesDB) : NotesRepository,
    CategoriesRepository,
    TagsRepository {

    private val _allNotes = MutableLiveData<List<Notes>>()
    val allNotes: LiveData<List<Notes>>
        get() = _allNotes

    private val _allCategories = MutableLiveData<List<Category>>()
    val allCategories: LiveData<List<Category>>
        get() = _allCategories

    private val _allTags = MutableLiveData<List<Tags>>()
    val allTags: LiveData<List<Tags>>
        get() = _allTags

    private val _tagNames = MutableLiveData<List<String>>()
    val tagNames: LiveData<List<String>>
        get() = _tagNames

    private var tagsList: List<Tags> = ArrayList()
    private var notesCount: Int = 0

    override suspend fun getNotes() {
        val notes = notesDB.noteDao().getAllNotes()
        Log.i("AllNotes", "Size: ${notes.size}")
        _allNotes.postValue(notes)
    }

    override suspend fun insertNote(notes: Notes) {
        notesDB.noteDao().insertNote(notes)
        getNotes()
        getAllCategories()
    }

    override suspend fun getNotesByCategory(categoryId: Int) {
        notesCount = notesDB.noteDao().getNotesByCategoryId(categoryId).size
    }

    override suspend fun updateNote(note: Notes) {
        notesDB.noteDao().updateNote(note)
        getNotes()
    }

    override suspend fun pinNote(note: Notes) {

    }

    override suspend fun unPinNote(note: Notes) {

    }

    override suspend fun deleteNote(note: Notes) {
        notesDB.noteDao().deleteNote(note)
    }

    override suspend fun getAllCategories() {
        val categories = notesDB.categoryDao().getAllCategories()
        categories.forEach {
            getNotesByCategory(it.id)
            it.notesCount = notesCount
        }
        _allCategories.postValue(categories)
    }

    override suspend fun insertCategory(category: Category) {
        notesDB.categoryDao().insertCategory(category)
    }

    override suspend fun updateCategory(category: Category) {
        notesDB.categoryDao().updateCategory(category)
        getAllCategories()
    }

    fun getCategoryId(name: String): Int {
        _allCategories.value?.forEach {
            if (it.categoryName == name) {
                return it.id
            }
        }
        return 0
    }

    override suspend fun insertTag(tags: Tags) {
        notesDB.tagsDao().insertTag(tags)
        getAllTags()
    }

    override suspend fun getAllTags() {
        tagsList = notesDB.tagsDao().getAllTags()
        _allTags.postValue(tagsList)
    }

    override suspend fun getTagsName(tagsId: ArrayList<Int>) {
        getAllTags()
        val list: MutableList<String> = ArrayList()
        tagsList.forEach {
            if (tagsId.contains(it.id)) {
                list.add(it.tagName)
            }
        }
        _tagNames.postValue(list)
    }

    override suspend fun updateTag(tags: Tags) {
        notesDB.tagsDao().updateTag(tags)
    }

    override suspend fun deleteTag(tags: Tags) {
        notesDB.tagsDao().deleteTag(tags)
    }

}