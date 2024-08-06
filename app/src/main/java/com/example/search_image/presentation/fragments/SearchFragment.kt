package com.example.search_image.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.search_image.databinding.FragmentSearchBinding
import com.example.search_image.presentation.MainAdapter
import com.example.search_image.presentation.MainViewModel

class SearchFragment : Fragment() {
    private val binding: FragmentSearchBinding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private lateinit var mainAdapter: MainAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        return binding.root //제발 여기 신경 좀 쓰기...
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainAdapter = MainAdapter()

        mainViewModel.communicateNetWork()

        Log.d("Viewmodel? 제대로?", mainViewModel.searchQuery)
        Log.d("items?", mainViewModel.items.toString())

        mainAdapter.itemClick = object : MainAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                Log.d("클릭 했슈~", "클릭클릭")
            }
        }

        binding.mainRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = mainAdapter
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

    private fun setUpImageSearchParameter(query: String): HashMap<String, String> {
        return hashMapOf(
            "query" to query, // **required** 검색을 원하는 질의어
            "sort" to "accuracy", // 결과 문서 정렬 방식 // accuracy: 정확도순 (default) / recency: 최신순
            "page" to "20", // 결과 페이지 번호, 1~50 사이의 값, 기본 값 1
            "size" to "1", // 한 페이지에 보여질 문서 수, 1~80 사이의 값, 기본 값 80
        )
    }
}