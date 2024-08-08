package com.example.search_image.presentation

import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.search_image.data.model.ImageDocument
import com.example.search_image.data.model.ImageResultData
import com.example.search_image.data.model.MyResultData
import com.example.search_image.network.NetworkClient
import kotlinx.coroutines.launch

// 정우님이 도와주신 코드~~
fun List<ImageDocument>.toMyData(): List<MyResultData> {
    return this.map{
        MyResultData(
            it.datetime,
            it.displaySitename,
            it.thumbnailUrl,
        )
    }
}

class MainViewModel:ViewModel() {
    private val _itemList = MutableLiveData<List<MyResultData>>()
    val itemList: LiveData<List<MyResultData>> = _itemList

    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery: LiveData<String> get() = _searchQuery

    fun communicateNetWork() = viewModelScope.launch() {
        try {
            val responseData: ImageResultData? = NetworkClient.searchImageNetwork.getSearchResultList(query = searchQuery.value ?: "") //내가 만들어둔 interface 호출
            Log.d("responseData>>", responseData?.documents?.toMyData().toString())
            _itemList.postValue(responseData?.documents?.toMyData())
        } catch (e: Exception) {
            // 에러 처리
        }
    }

    fun onSearchQeury(newText: String, inputMethodManager: InputMethodManager, view: View) {
        _searchQuery.value = newText

        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0);
        view.clearFocus()
    }

    fun saveSearchQuery(pref: SharedPreferences, newText: String){
        val edit = pref.edit()

        edit.putString("search_query", newText)
        edit.apply()
    }

    fun selectMyList(position : Int, item: MyResultData) {
//        selectedList.add(item.copy(isSelected = true))
//        _isSelectedList.value = selectedList

//        _isSelectedList.postValue(listOf(item.copy(isSelected = true))) << postValue 쓰면 즉각적인 반영이 안 됨


        // 명선님이 도와주신 코드~~
        _itemList.value = _itemList.value?.mapIndexed{ index, item ->
            if(position == index) {
                item.copy(isSelected = !item.isSelected)
            } else {
                item.copy()
            }
        }
    }

    fun loadSearchQuery(pref: SharedPreferences, textField: EditText) {
        textField.setText(pref.getString("search_query",""))
    }

    fun saveMyDrawer(pref: SharedPreferences) {
        val edit = pref.edit()

//        edit.
    }

    fun loadMyDrawer() {

    }

}