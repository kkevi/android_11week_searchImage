package com.example.search_image.presentation.fragments

import android.annotation.SuppressLint
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
        mainAdapter = MainAdapter()


        with(mainViewModel){
            loadMyDrawer()
            mainAdapter.submitList(selectedList.value)

            itemList.observe(viewLifecycleOwner){ itemList ->
                mainAdapter.submitList(selectedList.value)
            }


            // 아이템 눌렀을 때 동작하는 함수
            mainAdapter.itemClick = object : MainAdapter.ItemClick {
                @SuppressLint("NotifyDataSetChanged")
                override fun onClickItem(position: Int, item: MyResultData) {
                    unselectMyList(item)
                    saveMyDrawer(selectedList.value ?: listOf())
                    mainAdapter.notifyDataSetChanged()
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