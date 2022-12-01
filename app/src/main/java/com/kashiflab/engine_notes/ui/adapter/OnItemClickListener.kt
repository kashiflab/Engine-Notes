package com.kashiflab.engine_notes.ui.adapter

interface OnItemClickListener<T> {
    fun onNoteClick(data: T)
    fun onNoteLongClick(data: T, position: Int)
}