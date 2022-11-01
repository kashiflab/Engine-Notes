package com.kashiflab.engine_notes.ui.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.kashiflab.engine_notes.data.models.Category
import com.kashiflab.engine_notes.databinding.FragmentCategoriesBinding
import com.kashiflab.engine_notes.ui.adapter.CategoryAdapter
import com.kashiflab.engine_notes.ui.adapter.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment(), OnItemClickListener<Category> {

    private var _binding: FragmentCategoriesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val categoriesViewModel =
            ViewModelProvider(this)[CategoriesViewModel::class.java]

        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.categoriesRV.layoutManager = GridLayoutManager(context, 2)
        binding.categoriesRV.setHasFixedSize(true)

        adapter = CategoryAdapter(emptyList(), this)
        binding.categoriesRV.adapter = adapter

        categoriesViewModel.allCategories.observe(viewLifecycleOwner, Observer {
            adapter.setCategoriesList(it)
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onNoteClick(data: Category) {
        Toast.makeText(context, "Category Clicked", Toast.LENGTH_SHORT).show()
    }
}