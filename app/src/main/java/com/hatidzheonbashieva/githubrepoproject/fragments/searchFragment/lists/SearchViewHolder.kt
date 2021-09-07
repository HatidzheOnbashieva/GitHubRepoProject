package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment.lists

import androidx.recyclerview.widget.RecyclerView
import com.hatidzheonbashieva.githubrepoproject.databinding.SearchItemBinding
import com.hatidzheonbashieva.githubrepoproject.model.Repos
import com.squareup.picasso.Picasso

class SearchViewHolder(private val viewBinding: SearchItemBinding) : RecyclerView.ViewHolder(viewBinding.root){

    fun setUserData(userData: Repos, onRowClick: (Repos) -> Unit){
        Picasso.get().load(userData.users?.avatarUrl).into(viewBinding.profileImage)
        viewBinding.username.text = userData.users?.username
        viewBinding.repoName.text = userData.repoName

        viewBinding.root.setOnClickListener{
            onRowClick.invoke(userData)
        }
    }
}