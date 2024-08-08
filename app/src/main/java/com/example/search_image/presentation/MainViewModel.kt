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

class MainViewModel:ViewModel() {
    private val _itemList = MutableLiveData<List<ImageDocument>>()
    val itemList: LiveData<List<ImageDocument>> = _itemList
    private var searchQuery: String = ""

    private val _isSelectedList = MutableLiveData<List<MyResultData>>()
    val isSelectedList: LiveData<List<MyResultData>> get() = _isSelectedList

    private var newList: MutableList<MyResultData> = mutableListOf()

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
    }

    fun saveSearchQuery(pref: SharedPreferences, newText: String){
        val edit = pref.edit() // 수정 모드

        // 1번째 인자는 키, 2번째 인자는 실제 담아둘 값
        edit.putString("search_query", newText)
        edit.apply() //
    }

    // 명선님이 도와주신 코드~~
    fun selectMyList(position : Int, item: MyResultData) {
//        _isSelectedList.postValue(listOf(item.copy(isSelected = true)))
        newList.add(item.copy(isSelected = true))
        _isSelectedList.value = newList


        Log.d("클릭했슈~ $position", _isSelectedList.value.toString())


//        _isSelectedList.value = _itemList.value?.mapIndexed { index, myResultData ->
//            Log.d("positino >>>>>", "${position} and $index")
//            if(index == position){
//                Log.d("positino", position.toString())
//                myResultData.copy(isSelected = true)
//            } else {
//                myResultData.copy()
//            }
//        }
    }

    fun loadSearchQuery(pref: SharedPreferences, textField: EditText) {
        // 1번째 인자는 키, 2번째 인자는 데이터가 존재하지 않을경우의 값
        textField.setText(pref.getString("search_query",""))
    }

    fun saveMyDrawer(pref: SharedPreferences) {
        val edit = pref.edit()

//        edit.
    }

    fun loadMyDrawer() {

    }

}