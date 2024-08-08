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

    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery: LiveData<String> get() = _searchQuery

    private val _isSelectedList = MutableLiveData<List<MyResultData>>()
    val isSelectedList: LiveData<List<MyResultData>> get() = _isSelectedList

    val bindingList = mutableListOf<MyResultData>()

    var selectedList: MutableList<MyResultData> = mutableListOf()

    fun communicateNetWork() = viewModelScope.launch() {
        try {
            val responseData: ImageResultData? = NetworkClient.searchImageNetwork.getSearchResultList(query = searchQuery.value ?: "") //내가 만들어둔 interface 호출
            _itemList.postValue(responseData?.documents)

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
        // MyResultData의 val isSelected: Boolean = false 로 하려고 하니까 너무 어려워서 포기
        selectedList.add(item.copy(isSelected = true))
        _isSelectedList.value = selectedList
//        _isSelectedList.postValue(listOf(item.copy(isSelected = true))) << postValue 쓰면 즉각적인 반영이 안 됨

        Log.d("💡 >>", isSelectedList.value.toString())


        // 명선님이 도와주신 코드~~
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
        textField.setText(pref.getString("search_query",""))
    }

    fun saveMyDrawer(pref: SharedPreferences) {
        val edit = pref.edit()

//        edit.
    }

    fun loadMyDrawer() {

    }

}