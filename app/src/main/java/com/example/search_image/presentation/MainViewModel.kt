package com.example.search_image.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.search_image.data.ImageBody
import com.example.search_image.retrofit.NetworkClient
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    var items = mutableListOf<ImageBody>()
    val searchQuery: String = "에스파"

    fun communicateNetWork() = viewModelScope.launch() {
        val responseData = NetworkClient.searchImageNetwork.getSearchResultList(query = searchQuery) //내가 만들어둔 interface 호출

//        items = responseData.response.imageBody

        // 별도 스레드에서는 UI 스레드에 있는 것들을 조작할 수 없기 때문에 아래 함수에서 조작해야함
//        runOnUiThread {
//            binding.spinnerViewGoo.setItems(goo)
//        }
    }
}