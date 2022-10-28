package com.kashiflab.engine_notes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "check_table")
data class Check(
    @PrimaryKey
    val id: Int,
    val check: Boolean,
    val title: String,
    val afterText: String,
    val beforeText: String,
)
