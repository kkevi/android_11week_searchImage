package com.example.search_image.data.model

import com.google.gson.annotations.SerializedName

data class MyResultData (
    val datetime: String, // 미리보기 이미지 URL
    @SerializedName("display_sitename")
    val displaySitename: String, //	이미지 URL
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String, //문서 URL
    val isSelected: Boolean = false,
)