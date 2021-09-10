package com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment.lists

import androidx.recyclerview.widget.RecyclerView
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity
import com.hatidzheonbashieva.githubrepoproject.databinding.StarredItemBinding
import com.squareup.picasso.Picasso

class StarredViewHolder(private val viewBinding: StarredItemBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun setUserData(userData: RepoEntity, onRowClick: (RepoEntity) -> Unit) {
        Picasso.get().load(userData.avatarUrl).into(viewBinding.profileImage)
        viewBinding.username.text = userData.username
        viewBinding.repoName.text = userData.repoName

        viewBinding.root.setOnClickListener {
            onRowClick.invoke(userData)
        }
    }
}