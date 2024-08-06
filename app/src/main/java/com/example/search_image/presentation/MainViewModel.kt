package com.example.search_image.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.search_image.data.model.ImageDocument
import com.example.search_image.data.model.ImageResultData
import com.example.search_image.network.NetworkClient
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    private val _itemList = MutableLiveData<List<ImageDocument>>()
    val itemList: LiveData<List<ImageDocument>> = _itemList
    var items = mutableListOf<ImageDocument>()
    val searchQuery: String = "에스파"

    fun communicateNetWork() = viewModelScope.launch() {
        try {
            val responseData: ImageResultData? = NetworkClient.searchImageNetwork.getSearchResultList(query = searchQuery) //내가 만들어둔 interface 호출
            _itemList.postValue(responseData?.documents)
        } catch (e: Exception) {
            // 에러 처리
        }
    }
}