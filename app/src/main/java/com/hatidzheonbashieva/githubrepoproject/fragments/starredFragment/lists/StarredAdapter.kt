package com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity
import com.hatidzheonbashieva.githubrepoproject.databinding.StarredItemBinding

class StarredAdapter(var onRowClick: (RepoEntity) -> Unit) :
    RecyclerView.Adapter<StarredViewHolder>() {

    var repoEntity: ArrayList<RepoEntity> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarredViewHolder {
        val viewBinding =
            StarredItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return StarredViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: StarredViewHolder, position: Int) {
        val repoData = repoEntity[position]
        holder.setUserData(repoData, onRowClick)
    }

    override fun getItemCount(): Int {
        return repoEntity.size
    }
}