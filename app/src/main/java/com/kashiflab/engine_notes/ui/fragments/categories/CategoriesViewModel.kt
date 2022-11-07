package com.kashiflab.engine_notes.ui.fragments.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kashiflab.engine_notes.data.models.Category
import com.kashiflab.engine_notes.data.repository.CategoriesRepository
import com.kashiflab.engine_notes.data.repository.MainRepository
import com.kashiflab.engine_notes.data.utils.AppUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {

    val allCategories : LiveData<List<Category>>
    get() = repo.allCategories



    init {
        getAllCategories()
    }

    private fun updateCategory(category: Category){
        viewModelScope.launch {
            repo.updateCategory(category)
        }
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            repo.getAllCategories()
        }
    }
}