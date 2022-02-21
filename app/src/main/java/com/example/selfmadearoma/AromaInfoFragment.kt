package com.example.selfmadearoma

import android.R
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.selfmadearoma.adapters.AromaOilAdapter
import com.example.selfmadearoma.databinding.FragmentAromaInfoBinding
import com.example.selfmadearoma.viewmodels.AromaInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AromaInfoFragment : Fragment() {

    private lateinit var binding: FragmentAromaInfoBinding
    private val viewModel by viewModels<AromaInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAromaInfoBinding.inflate(inflater, container, false)

        val adapter = AromaOilAdapter(requireContext())
        binding.aromaOilRv.adapter = adapter
        binding.aromaOilRv.layoutManager = GridLayoutManager(context, 2)

        viewModel.aromaOilNames

        viewModel.aromaOilNames.observe(this, {
            adapter.submitList(viewModel.aromaOilNames.value)
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchAromaOil(newText!!)
                return true
            }
        })

        postponeEnterTransition()
        binding.aromaOilRv.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }


        return binding.root
    }

}