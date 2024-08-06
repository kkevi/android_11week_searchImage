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
    val collection: String, //	컬렉션
    val datetime: String, // 미리보기 이미지 URL
    @SerializedName("display_sitename")
    val displaySitename: String, //	이미지 URL
    @SerializedName("doc_url")
    val docUrl: String, // 이미지의 가로 길이
    val height: Int, // 이미지의 세로 길이
    @SerializedName("image_url")
    val imageUrl: String, // 출처
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String, //문서 URL
    val width: Int // 문서 작성시간, ISO 8601[YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
)