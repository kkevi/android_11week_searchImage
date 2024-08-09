package com.example.search_image.presentation

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.search_image.data.model.ImageDocument
import com.example.search_image.data.model.ImageResultData
import com.example.search_image.data.model.MyResultData
import com.example.search_image.data.model.MySavedData
import com.example.search_image.network.NetworkClient
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.util.UUID

// 정우님이 도와주신 코드~~
fun List<ImageDocument>.toMyData(): List<MyResultData> {
    return this.map{
        MyResultData(
            UUID.randomUUID().toString(),
            it.datetime,
            it.displaySitename,
            it.thumbnailUrl,
        )
    }
}

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val inputMethodManager = application.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    private val qeuryPref : SharedPreferences = application.getSharedPreferences("search_query", Context.MODE_PRIVATE)
    private val myListPref : SharedPreferences = application.getSharedPreferences("my_drawer", Context.MODE_PRIVATE)

    private val _itemList = MutableLiveData<List<MyResultData>>()
    val itemList: LiveData<List<MyResultData>> = _itemList

    private val _searchQuery = MutableLiveData("")
    private val searchQuery: LiveData<String> get() = _searchQuery

    private val _selectedList = MutableLiveData<MutableList<MyResultData>>()
    val selectedList: LiveData<MutableList<MyResultData>> = _selectedList

    private val gson = Gson()
    private var savedData = mutableListOf<MyResultData>()

    fun communicateNetWork() = viewModelScope.launch() {
        try {
            val imageResponseData: ImageResultData? = NetworkClient.searchImageNetwork.getSearchResultList(query = searchQuery.value ?: "") //내가 만들어둔 interface 호출
//            val videoResponseData:
            _itemList.postValue(imageResponseData?.documents?.toMyData())
            _itemList.value?.sortedByDescending{it.datetime} //이거 왜 안 됨...?
        } catch (e: Exception) {
            Log.d("❗️ communicateNetWork error", e.message.toString())
        }
    }

    fun selectMyList(position : Int) {
        // 명선님이 도와주신 코드~~
        _itemList.value = _itemList.value?.mapIndexed{ index, item ->
            if(position == index) {
                item.copy(isSelected = !item.isSelected)
            } else {
                item.copy()
            }
        }

        _selectedList.value = (itemList.value?.filter { it.isSelected }?.toMutableList() ?: mutableListOf())
            .apply { addAll(savedData.distinctBy { it.id }) }
    }

    fun unselectMyList(item: MyResultData){
        _selectedList.value?.remove(item)
    }

    fun onSearchQeury(newText: String, view: View) {
        _searchQuery.value = newText

        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0);
        view.clearFocus()
    }

    fun saveSearchQuery(newText: String){
        val edit = qeuryPref.edit()

        edit.putString("search_query", newText)
        edit.apply()
    }

    fun loadSearchQuery(textField: EditText) {
        val getFromPref = qeuryPref.getString("search_query","")
        textField.setText(getFromPref)
    }

    fun saveMyDrawer(listData: List<MyResultData>) {
        val edit = myListPref.edit()
        val jsonData: String = gson.toJson(MySavedData("saved", listData)
        )

        edit.putString("my_drawer", jsonData)
        edit.apply()
    }

    fun loadMyDrawer() {
        val getFromPref = myListPref.getString("my_drawer", "[]") ?: "[]"
        val jsonData = gson.fromJson(getFromPref, MySavedData::class.java)
        savedData = jsonData.itemList.toMutableList()

        _selectedList.value = jsonData.itemList.toMutableList()
    }
}