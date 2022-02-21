package com.example.selfmadearoma.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.selfmadearoma.AromaInfoFragmentDirections
import com.example.selfmadearoma.HomeViewPagerFragmentDirections
import com.example.selfmadearoma.R
import com.example.selfmadearoma.data.aromaOil.AromaOil
import com.example.selfmadearoma.data.myArom.MyAroma
import com.example.selfmadearoma.databinding.ListItemMyaromaBinding

class MyAromaAdapter :
    ListAdapter<MyAroma, MyAromaAdapter.ViewHolder>(
        MyAromaDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_myaroma,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemMyaromaBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                navigateToUserAromaDetailFragment(binding.myAroma!!, view)
            }
        }

        private fun navigateToUserAromaDetailFragment(myAroma: MyAroma, view: View) {

            val direction =
                HomeViewPagerFragmentDirections
                    .actionHomeViewPagerFragmentToUserAromaDetailFragment(myAroma.id, binding.cvMyAroma.transitionName)

            val extras = FragmentNavigatorExtras(
                binding.cvMyAroma to binding.cvMyAroma.transitionName
            )

            view.findNavController().navigate(direction, extras)
        }


        fun bind(item: MyAroma) {
            binding.apply {
                myAroma = item
                cvMyAroma.transitionName = item.id.toString()
                executePendingBindings()
            }
        }
    }
}

private class MyAromaDiffCallback : DiffUtil.ItemCallback<MyAroma>() {

    override fun areItemsTheSame(
        oldItem: MyAroma,
        newItem: MyAroma
    ): Boolean {
        return oldItem.myAromaName == newItem.myAromaName
    }

    override fun areContentsTheSame(
        oldItem: MyAroma,
        newItem: MyAroma
    ): Boolean {
        return oldItem == newItem
    }
}
