package com.kashiflab.engine_notes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kashiflab.engine_notes.R
import com.kashiflab.engine_notes.data.models.Category

class CategoryAdapter(private var categories: List<Category>,
                      private val listener: OnItemClickListener<Category>)
    : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.categoryTitle)
        val count = itemView.findViewById<TextView>(R.id.categoryNotesCount)
        val image = itemView.findViewById<ImageView>(R.id.categoryImage)
    }

    fun setCategoriesList(categories: List<Category>){
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val obj = categories[position]

        holder.title.text = "Work Notes"
        holder.count.text = "4 files"
        holder.image.setBackgroundResource(R.drawable.work_notes)
    }

    override fun getItemCount(): Int {
        return 5
    }
}