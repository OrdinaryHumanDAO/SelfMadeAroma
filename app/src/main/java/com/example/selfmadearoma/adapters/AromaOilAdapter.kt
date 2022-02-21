package com.example.selfmadearoma.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.selfmadearoma.AromaInfoFragmentDirections
import com.example.selfmadearoma.R
import com.example.selfmadearoma.data.aromaOil.AromaOil
import com.example.selfmadearoma.data.myArom.MyAroma
import com.example.selfmadearoma.databinding.ListItemAromaoilBinding
import com.example.selfmadearoma.databinding.ListItemMyaromaBinding

class AromaOilAdapter(private val context: Context) :
    ListAdapter<AromaOil, AromaOilAdapter.ViewHolder>(
        AromaOilDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_aromaoil,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        Glide.with(context)
            .load(getItem(position).aromaOilImage)
            .into(holder.itemView.findViewById(R.id.aromaOilImage))
    }

    class ViewHolder(
        private val binding: ListItemAromaoilBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                navigateToAromaInfoDetailFragment(binding.aromaOil!!, view)
            }
        }

        private fun navigateToAromaInfoDetailFragment(aromaOil: AromaOil, view: View) {
            val direction =
                AromaInfoFragmentDirections
                    .actionAromaInfoFragmentToAromaInfoDetailFragment(
                        aromaOil.aromaOilId,
                        binding.cvAromaOil.transitionName,
                        binding.aromaOilImage.transitionName
                    )

            val extras = FragmentNavigatorExtras(
                binding.cvAromaOil to binding.cvAromaOil.transitionName,
                binding.aromaOilImage to binding.aromaOilImage.transitionName
            )

            view.findNavController().navigate(direction, extras)
        }

        fun bind(item: AromaOil) {
            binding.apply {
                aromaOil = item
                cvAromaOil.transitionName = "transition_name_cv${item.aromaOilId}"
                aromaOilImage.transitionName = "transition_name_iv${item.aromaOilId}"
                executePendingBindings()
            }
        }
    }
}

private class AromaOilDiffCallback : DiffUtil.ItemCallback<AromaOil>() {

    override fun areItemsTheSame(
        oldItem: AromaOil,
        newItem: AromaOil
    ): Boolean {
        return oldItem.aromaOilId == newItem.aromaOilId
    }

    override fun areContentsTheSame(
        oldItem: AromaOil,
        newItem: AromaOil
    ): Boolean {
        return oldItem == newItem
    }
}