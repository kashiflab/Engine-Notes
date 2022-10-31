package com.kashiflab.engine_notes.ui.adapter

import com.kashiflab.engine_notes.data.models.Notes

interface OnNoteClickListener {
    fun onNoteClick(note: Notes)
}