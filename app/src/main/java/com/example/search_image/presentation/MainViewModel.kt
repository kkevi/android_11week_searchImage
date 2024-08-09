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
import com.example.search_image.data.model.MySavedData
import com.example.search_image.network.NetworkClient
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

// 정우님이 도와주신 코드~~
fun List<ImageDocument>.toMyData(): List<MyResultData> {
    return this.map{
        MyResultData(
            UUID.randomUUID().toString().toUpperCase(Locale.ROOT),
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

    private val _selectedList = MutableLiveData<MutableList<MyResultData>>()
    val selectedList: LiveData<MutableList<MyResultData>> = _selectedList

    private val gson = Gson()

    fun communicateNetWork() = viewModelScope.launch() {
        try {
            val responseData: ImageResultData? = NetworkClient.searchImageNetwork.getSearchResultList(query = searchQuery.value ?: "") //내가 만들어둔 interface 호출
            _itemList.postValue(responseData?.documents?.toMyData())
        } catch (e: Exception) {
            // 에러 처리
        }
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
//        Log.d("그냥 item", "구냥~ ${item.id}, ${item.thumbnailUrl}")
//        Log.d("itemList", "working~ ${itemList.value?.get(position)?.id}, ${itemList.value?.get(position)?.thumbnailUrl}")

        if(_selectedList.value?.isEmpty() == true){
            _selectedList.value = itemList.value?.filter { it.isSelected }?.toMutableList()
        } else {

        }

//        itemList.value?.filter {item.isSelected}?.let { _selectedList.value?.addAll(it) }
    }

    fun unselectMyList(position: Int, item: MyResultData){
        _selectedList.value?.remove(item)
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

    fun loadSearchQuery(pref: SharedPreferences, textField: EditText) {
        val getFromPref = pref.getString("search_query","")
        textField.setText(getFromPref)
    }

    fun saveMyDrawer(pref: SharedPreferences, listData: List<MyResultData>) {
        val edit = pref.edit()
        val jsonData: String = gson.toJson(MySavedData("saved", listData)
        )

        edit.putString("my_drawer", jsonData)
        edit.apply()
    }

    fun loadMyDrawer(pref: SharedPreferences) {
        val getFromPref = pref.getString("my_drawer", "[]") ?: "[]"
        val jsonData = gson.fromJson(getFromPref, MySavedData::class.java)

        _selectedList.value = jsonData.itemList.toMutableList()
    }
}