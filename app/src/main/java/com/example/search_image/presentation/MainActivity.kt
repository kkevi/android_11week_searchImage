package com.example.search_image.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.viewpager2.widget.ViewPager2
import com.example.search_image.R
import com.example.search_image.databinding.ActivityMainBinding
import com.example.search_image.presentation.fragments.MyDrawerFragment
import com.example.search_image.presentation.fragments.SearchFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val fragmentManager: FragmentManager = supportFragmentManager
    private lateinit var mainViewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFragment(SearchFragment())

        binding.tabLayout.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0 -> setFragment(SearchFragment())

                    1 -> setFragment(MyDrawerFragment())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) { }

            override fun onTabReselected(tab: TabLayout.Tab?) { }
        })
    }

    private fun setFragment(fragment: Fragment){
        fragmentManager.commit {
            replace(R.id.frame_layout, fragment)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }
}