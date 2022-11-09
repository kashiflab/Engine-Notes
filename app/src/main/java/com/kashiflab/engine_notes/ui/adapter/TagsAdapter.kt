package com.kashiflab.engine_notes.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.kashiflab.engine_notes.R
import com.kashiflab.engine_notes.data.models.Tags

class TagsAdapter(private var tagsList: List<Tags>,
                  private val listener: AddTagClickListener<String>,
                  private val listenerTag: OnItemClickListener<Tags>)
    : RecyclerView.Adapter<TagsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox = itemView.findViewById<CheckBox>(R.id.checkBox)
        val addTagBtn = itemView.findViewById<Button>(R.id.addTagBtn)
        val tagET = itemView.findViewById<EditText>(R.id.tagTitle)
    }

    private val SHOW_TYPE = 0
    private val ADD_TYPE = 1

    fun setTagsList(tagsList: List<Tags>){
        this.tagsList = tagsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =if(viewType == SHOW_TYPE){
            LayoutInflater.from(parent.context)
                .inflate(R.layout.bottom_sheet_item, parent, false)
        }else{
            LayoutInflater.from(parent.context)
                .inflate(R.layout.add_new_tag, parent, false)

        }

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position==tagsList.size){
            //add tag here
            holder.addTagBtn.setOnClickListener {
                holder.addTagBtn.visibility = View.GONE
                holder.tagET.visibility = View.VISIBLE
            }

            holder.tagET.setOnEditorActionListener { v, actionId, event ->
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    //do something
                    listener.onAddTagClick(holder.tagET.text.toString().trim())
                    holder.tagET.setText("")
                }
                false
            }
        }else{
            val obj = tagsList[position]

            holder.checkBox.text = obj.tagName
            holder.checkBox.isChecked = obj.isSelected
            holder.checkBox.setOnCheckedChangeListener(null)

            holder.checkBox.setOnCheckedChangeListener { _, b ->
                obj.isSelected = b
                listenerTag.onNoteClick(obj)
            }
        }
    }

    override fun getItemCount(): Int {
        return tagsList.size +1
    }

    override fun getItemViewType(position: Int): Int {
        return if(position==tagsList.size){
            ADD_TYPE
        }else{
            SHOW_TYPE
        }
    }
}