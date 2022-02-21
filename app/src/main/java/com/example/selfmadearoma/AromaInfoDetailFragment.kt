package com.example.selfmadearoma

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.*
import com.bumptech.glide.Glide
import com.example.selfmadearoma.databinding.FragmentAromaInfoDetailBinding
import com.example.selfmadearoma.viewmodels.AromaInfoDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AromaInfoDetailFragment : Fragment() {
    private val args: AromaInfoDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentAromaInfoDetailBinding
    private val viewModel by viewModels<AromaInfoDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val transition = TransitionSet().apply {
//            addTransition(ChangeBounds())
//            addTransition(ChangeTransform())
//        }
        val transition =
            TransitionInflater.from(requireContext()).inflateTransition(R.transition.change_cv_transform)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAromaInfoDetailBinding.inflate(inflater, container, false)

        viewModel.getAromaOil(args.aromaOilId)

        binding.cvAromaInfoDetail.transitionName = args.transitionNameCv
        binding.aromaOilImage.transitionName = args.transitionNameIv

        viewModel.aromaOilInfo.observe(this, {
            binding.viewModel = viewModel
            Glide.with(requireContext())
                .load(viewModel.aromaOilInfo.value!!.aromaOilImage)
                .into(binding.aromaOilImage)
        })

        return binding.root
    }
}