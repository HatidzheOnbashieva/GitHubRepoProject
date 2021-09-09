package com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hatidzheonbashieva.githubrepoproject.databinding.SearchItemBinding
import com.hatidzheonbashieva.githubrepoproject.model.Repos

class StarredAdapter(var onRowClick: (Repos) -> Unit) : RecyclerView.Adapter<StarredViewHolder>(){

    var repos: ArrayList<Repos> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarredViewHolder {
        val viewBinding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return StarredViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: StarredViewHolder, position: Int) {
        val repoData = repos[position]
        holder.setUserData(repoData, onRowClick)
    }

    override fun getItemCount(): Int {
        return repos.size
    }
}