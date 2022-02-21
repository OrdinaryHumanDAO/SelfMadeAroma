package com.example.selfmadearoma

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.selfmadearoma.databinding.FragmentAromaCreateResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AromaCreateResultFragment : Fragment() {

    private lateinit var binding: FragmentAromaCreateResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAromaCreateResultBinding.inflate(inflater, container, false)
        return binding.root
    }
}