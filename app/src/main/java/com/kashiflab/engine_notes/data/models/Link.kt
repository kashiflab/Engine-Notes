package com.kashiflab.engine_notes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "link_table")
data class Link(
    @PrimaryKey
    val id: Int,
    val embeddedLink: String,
    val text: String,
    val afterText: String,
    val beforeText: String,
)
