package com.example.search_image.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.search_image.databinding.FragmentMyDrawerBinding
import com.example.search_image.presentation.adapter.SearchAdapter
import com.example.search_image.presentation.MainViewModel

class MyDrawerFragment : Fragment() {
    private val binding: FragmentMyDrawerBinding by lazy { FragmentMyDrawerBinding.inflate(layoutInflater) }
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
        searchAdapter = SearchAdapter()

        Log.d("💡 내 보관함 itemList? 1단계 >>", mainViewModel.isSelectedList.value.toString())
        mainViewModel.isSelectedList.observe(viewLifecycleOwner){ itemList ->
            Log.d("💡 내 보관함 itemList 2단계 >>", itemList.toString())
            searchAdapter.addItems(itemList)
        }
        mainViewModel.loadMyDrawer()


        binding.mainRecyclerView2.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = searchAdapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MyDrawerFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}