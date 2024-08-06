package com.example.search_image.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.search_image.databinding.MainRecylcerViewListBinding

class MainAdapter: RecyclerView.Adapter<MainAdapter.Holder>() {
    interface ItemClick {
        fun onClick(view: View, position: Int)
//        fun onLongClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null

    inner class Holder(private val binding: MainRecylcerViewListBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.ivItemImage
        val title = binding.tvItemTitle
        val date = binding.tvItemDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = MainRecylcerViewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder){
            itemView.setOnClickListener {
                itemClick?.onClick(it, position)
            }

//            image.setImageResource()
            title.text = "제목 $position"
            date.text = "2024-08-05 20:00:00"
        }

    }

    // 아래 2개는 꼭 override 해줘야 함
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        // 리스트 총개수 반환
        return 10
    }
}