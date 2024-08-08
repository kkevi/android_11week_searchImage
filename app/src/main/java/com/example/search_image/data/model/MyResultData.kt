package com.example.search_image.data.model

data class MyResultData (
    val datetime: String,
    val displaySitename: String,
    val thumbnailUrl: String,
    var isSelected: Boolean = false,
)