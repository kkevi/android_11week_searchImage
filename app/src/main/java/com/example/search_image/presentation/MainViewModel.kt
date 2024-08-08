package com.example.search_image.presentation

import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
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
    var searchQuery: String = ""


    fun communicateNetWork() = viewModelScope.launch() {
        try {
            val responseData: ImageResultData? = NetworkClient.searchImageNetwork.getSearchResultList(query = searchQuery) //내가 만들어둔 interface 호출
            _itemList.postValue(responseData?.documents)
        } catch (e: Exception) {
            // 에러 처리
        }
    }

    fun onSearchQeury(newText: String, inputMethodManager: InputMethodManager, view: View) {
        searchQuery = newText

        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0);
        view.clearFocus()
        Log.d("매개변수", newText)
        Log.d("쿼리", searchQuery)
    }

    fun onSearchQeury() {

    }
}