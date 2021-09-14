package com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hatidzheonbashieva.githubrepoproject.R
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity
import com.hatidzheonbashieva.githubrepoproject.databinding.FragmentStarredBinding
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.DetailsFragment
import com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment.lists.StarredAdapter
import java.util.*
import kotlin.collections.ArrayList

class StarredFragment : Fragment() {

    private var viewBinding: FragmentStarredBinding? = null
    private val viewModel: StarredViewModel by viewModels()
    private lateinit var starredAdapter: StarredAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentStarredBinding.inflate(layoutInflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        viewBinding?.toolbar?.title = "Search for a user"
        (activity as AppCompatActivity).setSupportActionBar(viewBinding?.toolbar)
        setHasOptionsMenu(true)

        viewModel.getAllRepos().observe(requireActivity(), {
            updateRepoList(it)
        })

    }

    private fun setUpAdapter() {
        viewBinding?.starredRecyclerView?.layoutManager = LinearLayoutManager(activity)
        starredAdapter = StarredAdapter { repoItem ->
            goToDetailsFragment(repoItem)
        }
        viewBinding?.starredRecyclerView?.adapter = starredAdapter
    }

    private fun goToDetailsFragment(repoItem: RepoEntity) {
        val fragment = DetailsFragment()
        val bundle = Bundle()
        bundle.putInt("repoId", repoItem.repoId)
        bundle.putString("repoName", repoItem.repoName)
        bundle.putString("username", repoItem.username)
        bundle.putString("avatarUrl", repoItem.avatarUrl)
        bundle.putString("description", repoItem.description)
        bundle.putString("language", repoItem.language)
        bundle.putString("dateCreated", repoItem.dateCreated)
        bundle.putString("url", repoItem.url)
        fragment.arguments = bundle

        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment).addToBackStack("starredFragment")
            .commit()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateRepoList(repoList: List<RepoEntity>) {
        starredAdapter.repoEntity = repoList as ArrayList<RepoEntity>
        starredAdapter.notifyDataSetChanged()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchMenuItem = menu.findItem(R.id.searchRepos)
        val searchView = searchMenuItem?.actionView as SearchView

        searchView.queryHint = "Enter a username..."


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val searchText = newText.replace(" ", "")
                val filteredList: MutableList<RepoEntity> = mutableListOf()

                viewModel.getAllRepos().observe(requireActivity(), {
                    updateRepoList(it)
                })

                for (user in starredAdapter.repoEntity) {

                    if (user.username?.lowercase(Locale.getDefault())?.contains(searchText) == true) {
                        filteredList.add(user)
                    }
                    updateRepoList(filteredList)
                }
                return false
            }

        })
    }

}