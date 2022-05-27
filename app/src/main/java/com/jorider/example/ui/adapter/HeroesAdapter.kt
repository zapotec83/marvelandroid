package com.jorider.example.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jorider.domain.model.Heroes
import com.jorider.example.databinding.HeroesAdapterBinding
import com.jorider.example.extensions.load
import com.jorider.example.ui.activity.HeroesInfoActivity

class HeroesAdapter(private val context: Context) : ListAdapter<Heroes, HeroesAdapter.HeroesViewHolder>(HeroesComparator){

    override fun onBindViewHolder(holder: HeroesViewHolder, position: Int) {
        val heroes = getItem(position)
        holder.binding.heroesItemImg.load(heroes.image)
        holder.binding.heroesItemImg.setOnClickListener {
            HeroesInfoActivity.startActivity(context, heroes.id)
        }
    }

    fun setList(list: List<Heroes>) {
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesViewHolder {
        val binding = HeroesAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroesViewHolder(binding)
    }

    class HeroesViewHolder(val binding: HeroesAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    object HeroesComparator : DiffUtil.ItemCallback<Heroes>() {
        override fun areItemsTheSame(oldItem: Heroes, newItem: Heroes): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Heroes, newItem: Heroes): Boolean {
            return oldItem == newItem
        }
    }
}