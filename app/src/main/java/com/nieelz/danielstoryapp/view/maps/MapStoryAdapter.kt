package com.nieelz.danielstoryapp.view.maps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nieelz.danielstoryapp.database.remote.response.ListStoryItem
import com.nieelz.danielstoryapp.databinding.RowStoryBinding

class MapStoryAdapter(
    private var listStoryItem: List<ListStoryItem>
) : RecyclerView.Adapter<MapStoryAdapter.MyViewHolder>() {

    private lateinit var binding: RowStoryBinding
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListStoryItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = RowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    class MyViewHolder(private val binding: RowStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem) {

            Glide.with(binding.imgPhoto).load(data.photoUrl).into(binding.imgPhoto)
            binding.tvUsername.text = data.name
            binding.tvDescription.text = data.description
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = listStoryItem[position]
        holder.bind(story)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(story)
        }

    }

    override fun getItemCount(): Int = listStoryItem.size
}