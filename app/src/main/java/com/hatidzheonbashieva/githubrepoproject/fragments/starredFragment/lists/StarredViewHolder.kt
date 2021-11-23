package com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment.lists

import androidx.recyclerview.widget.RecyclerView
import com.hatidzheonbashieva.githubrepoproject.customObjects.CustomItemLayout
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity

//class StarredViewHolder(private val viewBinding: StarredItemBinding) :
//    RecyclerView.ViewHolder(viewBinding.root) {
//
//    fun setUserData(userData: RepoEntity, onRowClick: (RepoEntity) -> Unit) {
//        Picasso.get().load(userData.avatarUrl).into(viewBinding.profileImage)
//        viewBinding.username.text = userData.username
//        viewBinding.repoName.text = userData.repoName
//
//        viewBinding.root.setOnClickListener {
//            onRowClick.invoke(userData)
//        }
//    }
//}

class StarredViewHolder(private val customItemLayout: CustomItemLayout) :
    RecyclerView.ViewHolder(customItemLayout) {

    fun setUserData(userData: RepoEntity, onRowClick: (RepoEntity) -> Unit) {
        userData.repoName?.let { customItemLayout.displayName(it) }
        userData.username?.let { customItemLayout.displayUserName(it) }
        userData.avatarUrl?.let { customItemLayout.displayImage(it) }

        customItemLayout.setOnClickListener {
            onRowClick.invoke(userData)
        }
    }
}