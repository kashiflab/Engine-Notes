package com.kashiflab.engine_notes.data.repository

import com.kashiflab.engine_notes.data.models.Category

interface CategoriesRepository {
     suspend fun getAllCategories()
     suspend fun insertCategory(category: Category)
     suspend fun updateCategory(category: Category)
}