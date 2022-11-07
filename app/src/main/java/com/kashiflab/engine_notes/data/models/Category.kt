package com.kashiflab.engine_notes.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    var id: Int,
    var categoryName: String,
    var categoryIcon: Int,
    var createdOn: String,
    var createdBy: String,
    var modifiedOn: String,
    var modifiedBy: String,

){
    @Ignore
    var notesCount: Int = 0
}
