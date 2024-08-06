package com.example.search_image.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.search_image.data.model.ImageDocument
import com.example.search_image.data.model.ImageResultData
import com.example.search_image.network.NetworkClient
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel:ViewModel() {
    var items = mutableListOf<ImageDocument>()
    val textValue: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val searchQuery: String = "에스파"

    fun communicateNetWork() = viewModelScope.launch() {
        val responseData: Response<ImageResultData?> = NetworkClient.searchImageNetwork.getSearchResultList(query = searchQuery) //내가 만들어둔 interface 호출

        items = responseData.body()?.response?.documents?.toMutableList() ?: mutableListOf()

        Log.d("\uD83D\uDCA1\uD83D\uDCA1 data>>", responseData.toString())
        Log.d("\uD83D\uDCA1\uD83D\uDCA1 data item>>", items.toString())
    }
}