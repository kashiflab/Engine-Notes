package com.kashiflab.engine_notes.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    val id: Int,
    var title: String,
    var desc: String,
    var categoryId: Int = 0,
    var tag_id: List<Int> = emptyList(),
//    @Ignore
//    @Relation(entity = Notes::class,parentColumn = "note_id",entityColumn = "note_id")
//    val image: List<Image> = ArrayList(),
//    val link: List<Link>,
//    val checkList: List<Check>,
    val createdOn: String,
    val createdBy: String,
    var modifiedBy: String = "",
    var modifiedOn: String = "",
) : java.io.Serializable
