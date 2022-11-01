package com.kashiflab.engine_notes.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kashiflab.engine_notes.data.models.Category

@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM category_table")
    suspend fun getAllCategories() : List<Category>

    @Update
    suspend fun updateCategory(category: Category)
}