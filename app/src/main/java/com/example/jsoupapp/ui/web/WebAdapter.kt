package com.example.jsoupapp.ui.web

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.jsoupapp.data.remote.Source
import com.example.jsoupapp.databinding.FilmListRowBinding
import com.example.jsoupapp.util.DiffUtilResource

class WebAdapter(private val onClick : (url: String) -> Unit)
    : RecyclerView.Adapter<WebAdapter.WebViewHolder>() {

    private var list = emptyList<Source>()

    class WebViewHolder(val binding: FilmListRowBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebViewHolder {
         return WebViewHolder(FilmListRowBinding
        .inflate(LayoutInflater
        .from(parent.context),
        parent,false))
    }

    override fun onBindViewHolder(holder: WebViewHolder, position: Int) {
        val currentItem = list[position]

        holder.binding.ivFilm.load(currentItem.imageUrl)
        holder.binding.tvTitle.text = currentItem.name
        holder.binding.root.setOnClickListener {
            onClick.invoke(currentItem.profileLink)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(newData : List<Source>) {
        val diff = DiffUtilResource(list,newData)
        val diffResult = DiffUtil.calculateDiff(diff)
        list = newData
        diffResult.dispatchUpdatesTo(this)
    }
}