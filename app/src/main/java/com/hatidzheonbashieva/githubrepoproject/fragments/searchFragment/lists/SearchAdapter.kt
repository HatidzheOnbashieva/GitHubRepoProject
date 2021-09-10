package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hatidzheonbashieva.githubrepoproject.databinding.SearchItemBinding
import com.hatidzheonbashieva.githubrepoproject.model.Repos

class SearchAdapter(var onRowClick: (Repos) -> Unit) : RecyclerView.Adapter<SearchViewHolder>() {

    var repos: ArrayList<Repos> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val viewBinding =
            SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val repoData = repos[position]
        holder.setUserData(repoData, onRowClick)
    }

    override fun getItemCount(): Int {
        return repos.size
    }
}