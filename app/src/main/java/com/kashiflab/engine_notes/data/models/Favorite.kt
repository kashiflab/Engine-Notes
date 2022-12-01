package com.kashiflab.engine_notes.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_table")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "fav_id")
    var id: Int,
    var note_id: Int,
)
