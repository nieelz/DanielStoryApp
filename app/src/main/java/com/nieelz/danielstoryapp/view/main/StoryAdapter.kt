package com.nieelz.danielstoryapp.view.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nieelz.danielstoryapp.database.remote.response.ListStoryItem
import com.nieelz.danielstoryapp.databinding.RowStoryBinding
import com.nieelz.danielstoryapp.view.detail.DetailActivity


class StoryAdapter(private val context: Context, private val listStory: List<ListStoryItem>) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var binding: RowStoryBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = listStory[position]
            with(binding) {
                Glide.with(context).load(story.photoUrl).into(imgPhoto)
                tvUsername.text = story.name
                tvDescription.text = story.description

                root.setOnClickListener {
                    context.let {
                        it.startActivity(Intent(it, DetailActivity::class.java).apply {
                            putExtra(DetailActivity.EXTRA_STORY, story)
                        })
                    }
                }

        }
    }

    override fun getItemCount(): Int = listStory.size

}