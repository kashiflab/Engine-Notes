package com.kashiflab.engine_notes.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kashiflab.engine_notes.data.models.Tags

@Dao
interface TagsDao {

    @Insert
    suspend fun insertTag(tags: Tags)

    @Query("SELECT * FROM tags_table")
    suspend fun getAllTags() : List<Tags>

    @Query("SELECT * FROM tags_table WHERE tag_id=:myTagId")
    suspend fun getTagByID(myTagId: Int) : Tags

    @Delete
    suspend fun deleteTag(tag: Tags)

    @Update
    suspend fun updateTag(tag: Tags)
}