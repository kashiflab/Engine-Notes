package com.kashiflab.engine_notes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kashiflab.engine_notes.R
import com.kashiflab.engine_notes.data.models.Category

class CategoryAdapter(private var categories: List<Category>,
                      private val listener: OnItemClickListener<Category>,
                      private val isSelection: Boolean,
                      private var selectedCategory: String = "")
    : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.categoryTitle)
        val count = itemView.findViewById<TextView>(R.id.categoryNotesCount)
        val image = itemView.findViewById<ImageView>(R.id.categoryImage)

        val checkBox = itemView.findViewById<CheckBox>(R.id.checkBox)
    }

    fun setCategoriesList(categories: List<Category>){
        this.categories = categories
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view =if(isSelection){
            LayoutInflater.from(parent.context)
                .inflate(R.layout.bottom_sheet_item, parent, false)

        }else{
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_item, parent, false)

        }


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val obj = categories[position]
        if(isSelection){

            holder.checkBox.text = obj.categoryName

            holder.checkBox.setOnCheckedChangeListener(null)

            holder.checkBox.isChecked = obj.categoryName == selectedCategory

            holder.checkBox.setOnCheckedChangeListener { buttonView, _ ->
                selectedCategory = obj.categoryName
                notifyDataSetChanged()
                listener.onNoteClick(obj)
            }

        }else{
            holder.title.text = obj.categoryName
            holder.count.text = "${obj.notesCount} files"
            holder.image.setBackgroundResource(obj.categoryIcon)

            holder.itemView.setOnClickListener {
                listener.onNoteClick(obj)
            }
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}