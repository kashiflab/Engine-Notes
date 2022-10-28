package com.kashiflab.engine_notes.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "image_table",
    foreignKeys = [
        ForeignKey(
            entity = Notes::class,
            parentColumns = ["note_id"],
            childColumns = ["note_id"],onDelete = ForeignKey.SET_DEFAULT
        )
    ]
    )
data class Image(
    @PrimaryKey
    val id: Int,
    val note_id: Int,
    val image: String,
    val afterText: String,
    val beforeText: String,
)
