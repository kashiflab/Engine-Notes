package com.kashiflab.engine_notes.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kashiflab.engine_notes.R
import com.kashiflab.engine_notes.data.models.Notes

class NotesAdapter(private var notes: List<Notes>, private val listener: OnItemClickListener<Notes>) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.title)
        val desc = itemView.findViewById<TextView>(R.id.desc)
    }

    fun setNotesList(notes: List<Notes>){
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)

        return ViewHolder(view)
    }

    private var selected_position = -1

    fun setSelectedPosition(index : Int){
        selected_position = index
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        if (selected_position == position) {
            holder.itemView.setBackgroundResource(R.drawable.selected_note)

        } else {
            holder.itemView.setBackgroundResource(R.drawable.unselected_note)
        }
        if(note.desc.length>60){
            holder.desc.text = "${note.desc.substring(0,60)}..."
        }else{
            holder.desc.text = note.desc
        }

        holder.title.text = note.title

        holder.itemView.setOnLongClickListener {
            val pos = holder.adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                listener.onNoteLongClick(note, position)
                selected_position = pos
                notifyDataSetChanged()
            }
            true
        }

        holder.itemView.setOnClickListener {
            selected_position = -1
            notifyDataSetChanged()
            listener.onNoteClick(note)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

}