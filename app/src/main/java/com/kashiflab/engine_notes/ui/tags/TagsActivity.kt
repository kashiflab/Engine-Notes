package com.kashiflab.engine_notes.ui.tags

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.kashiflab.engine_notes.data.models.Tags
import com.kashiflab.engine_notes.data.utils.AppUtils
import com.kashiflab.engine_notes.databinding.ActivityTagsBinding
import com.kashiflab.engine_notes.ui.adapter.AddTagClickListener
import com.kashiflab.engine_notes.ui.adapter.OnItemClickListener
import com.kashiflab.engine_notes.ui.adapter.TagsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_tags.*

@AndroidEntryPoint
class TagsActivity : AppCompatActivity(), OnItemClickListener<Tags>, AddTagClickListener<String> {

    private lateinit var binding : ActivityTagsBinding

    private lateinit var adapter: TagsAdapter

    private val tagsViewModel : TagsViewModel by viewModels()

    private var tagsList: List<Tags> = ArrayList()
    private var tagsId : ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityTagsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPendingIntent()

        tagsRecyclerView.layoutManager = LinearLayoutManager(this)
        tagsRecyclerView.setHasFixedSize(true)

        adapter = TagsAdapter(emptyList(), this, this)
        tagsRecyclerView.adapter = adapter

        tagsViewModel.allTags.observe(this) { it ->
            if (it != null && it.isNotEmpty()) {
                it.forEach {
                    it.isSelected = tagsId.contains(it.id)
                }
                adapter.setTagsList(it)
                tagsList = it
            }

        }

        backBtn.setOnClickListener {
            val intent = Intent()
            intent.putIntegerArrayListExtra("tagsId", tagsId)
            setResult(RESULT_OK, intent)
            finish()
        }

    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putIntegerArrayListExtra("tagsId", tagsId)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun getPendingIntent(){
        if(intent.hasExtra("tagsId")){
            tagsId = intent.getIntegerArrayListExtra("tagsId")!!
        }
    }

    override fun onNoteClick(data: Tags) {
        if(data.isSelected){
            tagsId.add(data.id)
        }else {
            tagsId.remove(data.id)
        }

    }

    override fun onAddTagClick(data: String) {
        if(tagsList.isNotEmpty() && tagsList.any { it.tagName == data }){
            Toast.makeText(this,"Tag already exist",Toast.LENGTH_SHORT).show()
        }else{
            val tag = Tags(0, data, true, AppUtils.getDateTime(), "Me", "","")
            tagsViewModel.insertTag(tag)
        }
    }

    override fun onNoteLongClick(data: Tags, position: Int) {
//        TODO("Not yet implemented")
    }
}
