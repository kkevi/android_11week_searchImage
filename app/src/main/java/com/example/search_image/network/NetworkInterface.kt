package com.example.search_image.network

import com.example.search_image.data.model.ImageResultData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NetworkInterface {
    @GET("v2/search/image")
    suspend fun getSearchResultList(
        @Header("Authorization") apiKey: String = "KakaoAK 289337fd9508ee2470b24d643072221a",
        @Query("query") query: String,
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 80,
    ): ImageResultData?
}