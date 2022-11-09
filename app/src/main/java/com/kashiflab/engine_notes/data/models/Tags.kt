package com.kashiflab.engine_notes.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags_table")
data class Tags(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tag_id")
    var id: Int,
    var tagName: String,
    var isSelected: Boolean,
    var createdOn: String,
    var createdBy: String,
    var modifiedOn: String,
    var modifiedBy: String,
)
