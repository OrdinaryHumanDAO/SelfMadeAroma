package com.example.selfmadearoma

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.*
import com.example.selfmadearoma.databinding.FragmentUserAromaDetailBinding
import com.example.selfmadearoma.utilities.dp
import com.example.selfmadearoma.viewmodels.UserAromaDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserAromaDetailFragment : Fragment() {
    private val args: UserAromaDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentUserAromaDetailBinding
    private val viewModel by viewModels<UserAromaDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transition = TransitionSet().apply {
            addTransition(ChangeBounds())
        }
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserAromaDetailBinding.inflate(inflater, container, false)
        viewModel.getMyAroma(args.myAromaId)
        binding.viewModel = viewModel

        viewModel.myAromaInfo.observe(this, {
            binding.text.text = viewModel.getMyAromaName()
            binding.tvMyAromaAmount.text = "${viewModel.myAromaInfo.value!!.myAroma.amount} ml"
            binding.llMyAromaOilInfo.removeAllViews()
            setMyAromaOilsInfoOnTv()
        })

        binding.btnToUserAromaEdit.setOnClickListener {
            navigateToUserAromaEditFragment()
        }

        binding.cvUserAromaDetail.transitionName = args.transitionName

        return binding.root
    }

    fun setMyAromaOilsInfoOnTv(){
        for(i in viewModel.myAromaInfo.value!!.myAromaOilInfo){
            val tv = TextView(requireContext())
            tv.text = "${i.aromaOilName}       ${i.aromaOilAmount} ml"
            tv.textSize = 20F
            binding.llMyAromaOilInfo.addView(tv)
        }
    }

    private fun navigateToUserAromaEditFragment() {
        val direction = UserAromaDetailFragmentDirections
            .actionUserAromaDetailFragmentToUserAromaEditFragment(viewModel.myAromaInfo.value!!.myAroma.id)
        view!!.findNavController().navigate(direction)
    }
}