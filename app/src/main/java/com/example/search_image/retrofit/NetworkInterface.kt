package com.example.search_image.retrofit

import com.example.search_image.data.ImageSearchData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NetworkInterface {
    @GET("v2/search/image")
    suspend fun getSearchResultList(
        @Header("Authorization") apiKey: String = "KakaoAK 289337fd9508ee2470b24d643072221a",
        @Query("query") query : String,
        @Query("sort") sort : String = "accuracy",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): ImageSearchData
}