package com.example.search_image.data.model

import com.google.gson.annotations.SerializedName

//data class ImageResultData(val response: ImageData)

data class ImageResultData(
    val documents: List<ImageDocument>,
    val meta: ImageMeta
)

data class ImageMeta(
    @SerializedName("is_end")
    val isEnd: Boolean,
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("total_count")
    val totalCount: Int
)

data class ImageDocument(
    val collection: String,
    val datetime: String,
    @SerializedName("display_sitename")
    val displaySitename: String,
    @SerializedName("doc_url")
    val docUrl: String,
    val height: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    val width: Int
)