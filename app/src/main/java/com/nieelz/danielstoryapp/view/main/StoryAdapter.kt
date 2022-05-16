package com.nieelz.danielstoryapp.view.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nieelz.danielstoryapp.database.local.story.ListStoryItem
import com.nieelz.danielstoryapp.databinding.RowStoryBinding


class StoryAdapter(private val context: Context, private val listStory : List<ListStoryItem>): RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    private lateinit var binding: RowStoryBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = listStory[position]
        with(binding){
            Glide.with(context).load(story.photoUrl).into(imgPhoto)
            tvUsername.text = story.name
            tvDescription.text = story.description
        }
    }

    override fun getItemCount(): Int = listStory.size


}