package com.example.search_image.data.model

data class MyResultData (
    val id: String,
    val datetime: String,
    val displaySitename: String,
    val thumbnailUrl: String,
    var isSelected: Boolean = false,
)

data class MySavedData(
    val name: String,
    val itemList: List<MyResultData> // 예시: 문자열 리스트
)