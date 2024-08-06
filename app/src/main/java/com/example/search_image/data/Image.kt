package com.example.search_image.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ImageSearchData(val response: ImageResponse)

data class ImageResponse(
    @SerializedName("meta")
    val imageHeader: ImageHeader,
    @SerializedName("documents")
    val imageBody: MutableList<ImageBody>
)

data class ImageHeader(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("is_end")
    val isEnd: Boolean
)

data class ImageBody(
//    val collection:	String, //	컬렉션
    val thumbnailUrl:	String,	// 미리보기 이미지 URL
    val imageUrl:	String, //	이미지 URL
    val width:	Int, // 이미지의 가로 길이
    val height:	Int, // 이미지의 세로 길이
    val displaySitename: String, // 출처
//    val docUrl: String, //문서 URL
    val datetime: LocalDateTime, //	문서 작성시간, ISO 8601[YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
)