package com.example.search_image.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.search_image.data.model.MyResultData
import com.example.search_image.databinding.MainRecylcerViewListBinding
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class MyDrawerAdapter(): RecyclerView.Adapter<MyDrawerAdapter.Holder>() {
    interface ItemClick {
        fun onClickItem(position: Int, item : MyResultData)
    }
    var itemClick: ItemClick? = null
    private val itemList = mutableListOf<MyResultData>()

    fun addItems(itm: List<MyResultData>){
        val positionStart = itemList.size
        itemList.addAll(itm)
        notifyItemRangeInserted(positionStart, itm.size)
    }

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
        val timestamp = OffsetDateTime.parse(itemList[position].datetime)

        with(holder){
            itemView.setOnClickListener {
                itemClick?.onClickItem(position, itemList[position])
            }

            Glide.with(itemView).load(itemList[position].thumbnailUrl).into(image)
            title.text = itemList[position].displaySitename
            date.text = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            star.visibility = if(itemList[position].isSelected) View.VISIBLE else View.GONE
        }

    }

    // 아래 2개는 꼭 override 해줘야 함
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        // 리스트 총개수 반환
        return itemList.size
    }
}