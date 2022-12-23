package com.example.jsoupapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jsoupapp.models.News
import com.example.jsoupapp.databinding.RvHomeItemBinding
import com.example.jsoupapp.util.DiffUtilResource

class HomeRvAdapter: RecyclerView.Adapter<HomeRvAdapter.HomeViewHolder>() {

    private var list = emptyList<News>()

    class HomeViewHolder(val binding: RvHomeItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
         return HomeViewHolder(RvHomeItemBinding
        .inflate(LayoutInflater
        .from(parent.context),
        parent,false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentItem = list[position]

        holder.binding.news = currentItem
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(newData : List<News>) {
        val diff = DiffUtilResource(list,newData)
        val diffResult = DiffUtil.calculateDiff(diff)
        list = newData
        diffResult.dispatchUpdatesTo(this)
    }
}