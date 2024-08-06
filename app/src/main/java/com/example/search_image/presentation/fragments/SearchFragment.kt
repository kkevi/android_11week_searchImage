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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainAdapter = MainAdapter()

        mainViewModel.communicateNetWork()

        Log.d("💡💡Viewmodel? 제대로?", mainViewModel.searchQuery)
        Log.d("\uD83D\uDCA1\uD83D\uDCA1 items?", mainViewModel.items.toString())

        mainAdapter.itemClick = object : MainAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                Log.d("클릭 했슈~", "클릭클릭")
            }
        }

        binding.mainRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = mainAdapter
        }

        // 별도 스레드에서는 UI 스레드에 있는 것들을 조작할 수 없기 때문에 아래 함수에서 조작해야함
//        runOnUiThread {
//            binding.spinnerViewGoo.setItems(goo)
//        }
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