package com.example.search_image.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.search_image.data.model.MyResultData
import com.example.search_image.databinding.FragmentSearchBinding
import com.example.search_image.presentation.adapter.SearchAdapter
import com.example.search_image.presentation.MainViewModel

class SearchFragment : Fragment() {
    private val binding: FragmentSearchBinding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private lateinit var searchAdapter: SearchAdapter
    private val mainViewModel: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val qeuryPref : SharedPreferences = requireContext().getSharedPreferences("search_query", Context.MODE_PRIVATE)
        val myListPref : SharedPreferences = requireContext().getSharedPreferences("my_drawer", Context.MODE_PRIVATE)

        searchAdapter = SearchAdapter()

        // 라이브 데이터 관찰자
        mainViewModel.itemList.observe(viewLifecycleOwner){ itemList ->
//            searchAdapter.addItems(itemList)

            for(item in itemList){
                val myResultData = MyResultData(
                    item.datetime,
                    item.displaySitename,
                    item.thumbnailUrl,
                )
                mainViewModel.bindingList.addAll(listOf(myResultData))

                searchAdapter.addItems(mainViewModel.bindingList)
            }
        }


        // 검색버튼 눌렀을 때 동작하는 함수
        binding.searchButton.setOnClickListener {
            with(mainViewModel){
                onSearchQeury(binding.searchBarTextField.text.toString(), inputMethodManager, view)
                saveSearchQuery(qeuryPref, binding.searchBarTextField.text.toString())
                communicateNetWork()
            }
        }


        // 아이템 눌렀을 때 동작하는 함수
        searchAdapter.itemClick = object : SearchAdapter.ItemClick {
            override fun onClickItem(position: Int, item: MyResultData) {
                mainViewModel.selectMyList(position, item)
                Log.d("asdfasdf", item.isSelected.toString())
            }
        }

        // 검색어 불러오기
        mainViewModel.loadSearchQuery(qeuryPref, binding.searchBarTextField)

        binding.mainRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = searchAdapter
        }

    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            SearchFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}