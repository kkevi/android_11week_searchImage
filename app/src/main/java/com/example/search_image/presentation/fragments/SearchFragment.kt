package com.example.search_image.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.search_image.data.model.MyResultData
import com.example.search_image.databinding.FragmentSearchBinding
import com.example.search_image.presentation.adapter.MainAdapter
import com.example.search_image.presentation.MainViewModel

class SearchFragment : Fragment() {
    private val binding: FragmentSearchBinding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private lateinit var mainAdapter: MainAdapter
    private val mainViewModel: MainViewModel by activityViewModels<MainViewModel>()

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

        mainAdapter = MainAdapter()

        with(mainViewModel){
            itemList.observe(viewLifecycleOwner){ itemList ->
                mainAdapter.submitList(mainViewModel.itemList.value)
            }
            // 검색어 불러오기
            loadSearchQuery(qeuryPref, binding.searchBarTextField)

            // 아이템 눌렀을 때 동작하는 함수
            mainAdapter.itemClick = object : MainAdapter.ItemClick {
                override fun onClickItem(position: Int, item: MyResultData) {
                    selectMyList(position)
                    saveMyDrawer(myListPref, selectedList.value ?: listOf())
                }
            }

            with(binding){
                // 검색버튼 눌렀을 때 동작하는 함수
                searchButton.setOnClickListener {
                    onSearchQeury(searchBarTextField.text.toString(), inputMethodManager, view)
                    saveSearchQuery(qeuryPref, searchBarTextField.text.toString())
                    communicateNetWork()
                }

                btnTop.setOnClickListener {
                    mainRecyclerView.smoothScrollToPosition(0)
                }

                mainRecyclerView.apply {
                    layoutManager = GridLayoutManager(context, 2)
                    adapter = mainAdapter
                    mainAdapter.submitList(listOf())
                    //  addItemDecoration(GridSpaceItemDecoration(2, 20))
                }
            }
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

//    class GridSpaceItemDecoration(private val spanCount: Int, private val space: Int) : RecyclerView.ItemDecoration() {
//
//        override fun getItemOffsets(
//            outRect: Rect,
//            view: View,
//            parent: RecyclerView,
//            state: RecyclerView.State
//        ) {
//            val position = parent.getChildAdapterPosition(view)
//            val column = position % spanCount + 1      // 1부터 시작
//
//            // 첫 행 제외하고 상단 여백 추가
//            if (position >= spanCount) {
//                outRect.top = space
//            }
//            outRect.bottom = space
//            // 첫번째 열을 제외하고 좌측 여백 추가
//            if (column != 1) {
//                outRect.left = space
//            }
//        }
//    }