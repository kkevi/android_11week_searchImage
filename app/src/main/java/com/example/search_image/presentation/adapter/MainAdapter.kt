package com.example.search_image.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.search_image.data.model.MyResultData
import com.example.search_image.databinding.MainRecylcerViewListBinding
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class MainAdapter(): ListAdapter<MyResultData, MainAdapter.Holder>(DiffCallback()) {
    interface ItemClick {
        fun onClickItem(position: Int, item : MyResultData)
    }
    var itemClick: ItemClick? = null

    class Holder(private val binding: MainRecylcerViewListBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.ivItemImage
        val title = binding.tvItemTitle
        val date = binding.tvItemDate
        val star = binding.ivStar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = MainRecylcerViewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        val timestamp = OffsetDateTime.parse(item.datetime)

        with(holder){
            itemView.setOnClickListener {
                itemClick?.onClickItem(position, item)
                notifyItemChanged(position)
            }

            Glide.with(itemView).load(item.thumbnailUrl).into(image)
            title.text = item.displaySitename
            date.text = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            star.isVisible = item.isSelected
        }

    }
}

private class DiffCallback : DiffUtil.ItemCallback<MyResultData>() {
    override fun areItemsTheSame(oldItem: MyResultData, newItem: MyResultData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MyResultData, newItem: MyResultData): Boolean {
        return oldItem == newItem
    }
}