package com.kashiflab.engine_notes.data.models

import android.os.Parcelable
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
