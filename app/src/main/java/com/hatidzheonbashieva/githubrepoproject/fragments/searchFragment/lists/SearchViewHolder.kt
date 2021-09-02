package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment.lists

import androidx.recyclerview.widget.RecyclerView
import com.hatidzheonbashieva.githubrepoproject.databinding.SearchItemBinding
import com.hatidzheonbashieva.githubrepoproject.model.User
import com.squareup.picasso.Picasso

class SearchViewHolder(private val viewBinding: SearchItemBinding) : RecyclerView.ViewHolder(viewBinding.root){
    fun setUserData(userData: User){
        Picasso.get().load(userData.avatarUrl).into(viewBinding.profileImage)
        viewBinding.username.text = userData.username
        //viewBinding.repoName.text = userData.repoName

    }
}