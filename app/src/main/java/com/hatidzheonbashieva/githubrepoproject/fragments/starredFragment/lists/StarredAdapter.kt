package com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment.lists

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hatidzheonbashieva.githubrepoproject.customObjects.CustomItemLayout
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity

class StarredAdapter(var onRowClick: (RepoEntity) -> Unit) :
    RecyclerView.Adapter<StarredViewHolder>() {

    var repoEntity: ArrayList<RepoEntity> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarredViewHolder {
//        val viewBinding =
//            StarredItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//
//        return StarredViewHolder(viewBinding)
        val customItemLayoutView = CustomItemLayout(parent.context)

        customItemLayoutView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return StarredViewHolder(customItemLayoutView)
    }

    override fun onBindViewHolder(holder: StarredViewHolder, position: Int) {
        val repoData = repoEntity[position]
        holder.setUserData(repoData, onRowClick)
    }

    override fun getItemCount(): Int {
        return repoEntity.size
    }
}