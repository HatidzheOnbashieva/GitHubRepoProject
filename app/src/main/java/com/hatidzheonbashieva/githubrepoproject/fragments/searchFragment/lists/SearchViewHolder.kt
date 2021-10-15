package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment.lists

import androidx.recyclerview.widget.RecyclerView
import com.hatidzheonbashieva.githubrepoproject.CustomItemLayout
import com.hatidzheonbashieva.githubrepoproject.model.Repos

//class SearchViewHolder(private val viewBinding: SearchItemBinding) :
//    RecyclerView.ViewHolder(viewBinding.root) {
//
//    fun setUserData(userData: Repos, onRowClick: (Repos) -> Unit) {
//        Picasso.get().load(userData.users.avatarUrl).into(viewBinding.profileImage)
//        viewBinding.username.text = userData.users.username
//        viewBinding.repoName.text = userData.repoName
//
//        viewBinding.root.setOnClickListener {
//            onRowClick.invoke(userData)
//        }
//    }
//}

class SearchViewHolder(private val customItemLayout: CustomItemLayout) :
    RecyclerView.ViewHolder(customItemLayout) {

    fun setUserData(userData: Repos, onRowClick: (Repos) -> Unit) {
        customItemLayout.displayName(userData.repoName)
        customItemLayout.displayUserName(userData.users.username)
        customItemLayout.displayImage(userData.users.avatarUrl)

        customItemLayout.setOnClickListener {
            onRowClick.invoke(userData)
        }
    }
}