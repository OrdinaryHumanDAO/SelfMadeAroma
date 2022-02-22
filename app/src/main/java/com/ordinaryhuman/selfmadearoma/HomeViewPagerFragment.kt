package com.ordinaryhuman.selfmadearoma

import android.os.Bundle
import android.view.Gravity
import android.view.Gravity.CENTER
import android.view.Gravity.TOP
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ordinaryhuman.selfmadearoma.adapters.AromaOilAdapter
import com.ordinaryhuman.selfmadearoma.adapters.MyAromaAdapter
import com.ordinaryhuman.selfmadearoma.data.myArom.MyAroma
import com.ordinaryhuman.selfmadearoma.databinding.FragmentHomeViewPagerBinding
import com.ordinaryhuman.selfmadearoma.viewmodels.HomeViewPagerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentHomeViewPagerBinding
    private val viewModel by viewModels<HomeViewPagerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeViewPagerBinding.inflate(inflater, container, false)

        val adapter = MyAromaAdapter()
        binding.myAromaRv.adapter = adapter
        binding.myAromaRv.layoutManager = LinearLayoutManager(context)

        viewModel.myAromas.observe(this,{
            if(it.isNotEmpty()){
                adapter.submitList(it)
                binding.llHomeViewPager.gravity = TOP
                binding.text.isVisible = false
                binding.myAromaList.isVisible = true
                binding.myAromaRv.isVisible = true
            } else {
                binding.llHomeViewPager.gravity = CENTER
                binding.text.isVisible = true
                binding.myAromaList.isVisible = false
                binding.myAromaRv.isVisible = false
            }

        })

        postponeEnterTransition()
        binding.myAromaRv.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }


        return binding.root
    }

}