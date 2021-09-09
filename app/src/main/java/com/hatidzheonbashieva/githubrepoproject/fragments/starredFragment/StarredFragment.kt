package com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hatidzheonbashieva.githubrepoproject.R
import com.hatidzheonbashieva.githubrepoproject.databinding.FragmentSearchBinding
import com.hatidzheonbashieva.githubrepoproject.databinding.FragmentStarredBinding
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.DetailsFragment
import com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment.lists.StarredAdapter
import com.hatidzheonbashieva.githubrepoproject.model.Repos

class StarredFragment  : Fragment() {

    private var viewBinding: FragmentSearchBinding? = null
    private val viewModel: StarredViewModel by viewModels()
    private lateinit var searchAdapter: StarredAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewBinding = FragmentStarredBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setHasOptionsMenu(true)

        viewModel.readAllRepos.observe(requireActivity(), {
            updateRepoList(it)
        })
    }

    private fun setUpAdapter() {
        viewBinding?.searchRecyclerView?.layoutManager = LinearLayoutManager(activity)
        searchAdapter = StarredAdapter { repoItem ->
            goToDetailsFragment(repoItem)
        }
        viewBinding?.searchRecyclerView?.adapter = searchAdapter
    }

    private fun goToDetailsFragment(repoItem: Repos) {
        val fragment = DetailsFragment()
        val bundle = Bundle()
        bundle.putInt("repoId", repoItem.id)
        bundle.putString("repoName", repoItem.repoName)
        bundle.putString("username", repoItem.users?.username)
        bundle.putString("avatarUrl", repoItem.users?.avatarUrl)
        bundle.putString("description", repoItem.description)
        bundle.putString("language", repoItem.language)
        bundle.putString("dateCreated", repoItem.dateCreated)
        bundle.putString("url", repoItem.url)
        fragment.arguments = bundle

        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment).addToBackStack("searchFragment")
            .commit()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun updateRepoList(repoList: List<Repos>) {
        searchAdapter.repos = repoList as ArrayList<Repos>
        searchAdapter.notifyDataSetChanged()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchMenuItem = menu?.findItem(R.id.searchRepos)
        val searchView = searchMenuItem?.actionView as SearchView

        searchView.queryHint = "Search for a username..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(requireContext(), "Toast message from Starred Fragment", Toast.LENGTH_SHORT).show()

                searchMenuItem.collapseActionView()
                return false
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

}