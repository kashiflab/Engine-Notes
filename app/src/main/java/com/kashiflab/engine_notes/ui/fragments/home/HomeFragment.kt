package com.kashiflab.engine_notes.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kashiflab.engine_notes.R
import com.kashiflab.engine_notes.data.models.Notes
import com.kashiflab.engine_notes.databinding.FragmentHomeBinding
import com.kashiflab.engine_notes.ui.CreateNoteActivity
import com.kashiflab.engine_notes.ui.adapter.NotesAdapter
import com.kashiflab.engine_notes.ui.adapter.OnItemClickListener
import com.kashiflab.engine_notes.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*


@AndroidEntryPoint
class HomeFragment : Fragment(), OnItemClickListener<Notes>, MenuProvider {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var adapter: NotesAdapter

    private var notesList: MutableList<Notes> = ArrayList()

    private val mainViewModel : MainViewModel by viewModels()

    private var isMenuChanged = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        adapter = NotesAdapter( emptyList(),this)

        binding.noteRV.layoutManager = LinearLayoutManager(context)
        binding.noteRV.setHasFixedSize(true)
        binding.noteRV.adapter = adapter

        mainViewModel.allNotes.observe(viewLifecycleOwner, Observer {
            notesList.clear()
            if(it.isNotEmpty()){
                notesList.addAll(it)
                adapter.setNotesList(it)
                binding.nothingToShow.visibility = View.GONE
            }else{
                binding.nothingToShow.visibility = View.VISIBLE
            }
        })

        binding.addNoteBtn.setOnClickListener {
            startActivity(Intent(context, CreateNoteActivity::class.java))
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(isMenuChanged){
                    changeMenu()
                }else{
                    requireActivity().finish()
                }
            }
        })

        return binding.root
    }

    private fun changeMenu(){
        isMenuChanged = false
        adapter.setSelectedPosition(-1)

        toolbar.menu.findItem(R.id.delete).isVisible = false
        toolbar.menu.findItem(R.id.favorite).isVisible = false
        toolbar.menu.findItem(R.id.pin).isVisible = false
    }


    override fun onNoteClick(data: Notes) {
        startActivity(Intent(context, CreateNoteActivity::class.java).putExtra("note", data))
    }

    override fun onNoteLongClick(data: Notes, position: Int) {
        isMenuChanged = true

        toolbar.menu.findItem(R.id.delete).isVisible = true
        toolbar.menu.findItem(R.id.favorite).isVisible = true
        toolbar.menu.findItem(R.id.pin).isVisible = true

        toolbar.menu.findItem(R.id.pin).setOnMenuItemClickListener {
            Toast.makeText(context, "Pinned", Toast.LENGTH_SHORT).show()
            true
        }

        toolbar.menu.findItem(R.id.favorite).setOnMenuItemClickListener {
            Toast.makeText(context, "Favorite", Toast.LENGTH_SHORT).show()
            true
        }

        toolbar.menu.findItem(R.id.delete).setOnMenuItemClickListener {
            mainViewModel.deleteNote(data)
            notesList.remove(data)
            adapter.setNotesList(notesList)
            changeMenu()
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
            true
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu, menu)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

}
