package com.example.search_image.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.search_image.data.model.MyResultData
import com.example.search_image.databinding.FragmentMyDrawerBinding
import com.example.search_image.presentation.adapter.MainAdapter
import com.example.search_image.presentation.MainViewModel

class MyDrawerFragment : Fragment() {
    private val binding: FragmentMyDrawerBinding by lazy { FragmentMyDrawerBinding.inflate(layoutInflater) }
    private lateinit var mainAdapter: MainAdapter
    private val mainViewModel: MainViewModel by activityViewModels<MainViewModel>()
//    private val mainViewModel: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

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
        val myListPref : SharedPreferences = requireContext().getSharedPreferences("my_drawer", Context.MODE_PRIVATE)

        mainAdapter = MainAdapter()

        with(mainViewModel){
            itemList.observe(viewLifecycleOwner){ itemList ->
                mainAdapter.submitList(selectedList.value)
            }
            communicateNetWork()
            loadMyDrawer(myListPref)


            // 아이템 눌렀을 때 동작하는 함수
            mainAdapter.itemClick = object : MainAdapter.ItemClick {
                override fun onClickItem(position: Int, item: MyResultData) {
                    unselectMyList(position, item)
                }
            }
        }

        binding.mainRecyclerView2.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = mainAdapter
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