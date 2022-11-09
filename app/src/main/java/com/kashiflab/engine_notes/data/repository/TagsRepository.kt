package com.kashiflab.engine_notes.data.repository

import com.kashiflab.engine_notes.data.models.Tags

interface TagsRepository {
    suspend fun insertTag(tags: Tags)
    suspend fun getAllTags()
    suspend fun getTagsName(tagsId: ArrayList<Int>)
    suspend fun deleteTag(tags: Tags)
    suspend fun updateTag(tags: Tags)
}