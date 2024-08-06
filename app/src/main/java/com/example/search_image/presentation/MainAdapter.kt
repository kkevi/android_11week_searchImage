package com.example.search_image.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.search_image.data.model.ImageDocument
import com.example.search_image.data.model.ImageResultData
import com.example.search_image.databinding.MainRecylcerViewListBinding
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MainAdapter(
//    private val itemList: List<ImageDocument>
): RecyclerView.Adapter<MainAdapter.Holder>() {
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null
    private val itemList = mutableListOf<ImageDocument>()


    fun addItems(itm: List<ImageDocument>){
        val positionStart = itemList.size
        itemList.addAll(itm)
        notifyItemRangeInserted(positionStart, itm.size)
    }

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
        val timestamp = OffsetDateTime.parse(itemList[position].datetime)

        with(holder){
            itemView.setOnClickListener {
                itemClick?.onClick(it, position)
            }

//            image.setImageResource()
            title.text = itemList[position].displaySitename
            date.text = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
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