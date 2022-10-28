package com.kashiflab.engine_notes.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "notes_table")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    val id: Int,
    val title: String,
    val desc: String,
//    @Ignore
//    @Relation(entity = Notes::class,parentColumn = "note_id",entityColumn = "note_id")
//    val image: List<Image> = ArrayList(),
//    val link: List<Link>,
//    val checkList: List<Check>,
    val createdOn: String,
    val createdBy: String,
    val modifiedBy: String = "",
    val modifiedOn: String = "",
)
