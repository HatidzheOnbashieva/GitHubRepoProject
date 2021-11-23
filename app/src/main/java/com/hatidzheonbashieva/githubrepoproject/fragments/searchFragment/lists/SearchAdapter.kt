package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment.lists

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hatidzheonbashieva.githubrepoproject.customObjects.CustomItemLayout
import com.hatidzheonbashieva.githubrepoproject.model.Repos

class SearchAdapter(var onRowClick: (Repos) -> Unit) : RecyclerView.Adapter<SearchViewHolder>() {

    var repos: ArrayList<Repos> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {

//        val viewBinding =
//            SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//
//        return SearchViewHolder(viewBinding)

        val customItemLayoutView = CustomItemLayout(parent.context)

        customItemLayoutView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return SearchViewHolder(customItemLayoutView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val repoData = repos[position]
        holder.setUserData(repoData, onRowClick)
    }

    override fun getItemCount(): Int {
        return repos.size
    }
}