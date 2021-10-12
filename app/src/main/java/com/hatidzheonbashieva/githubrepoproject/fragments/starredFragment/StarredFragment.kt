package com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hatidzheonbashieva.githubrepoproject.MainViewModel
import com.hatidzheonbashieva.githubrepoproject.R
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity
import com.hatidzheonbashieva.githubrepoproject.databinding.FragmentStarredBinding
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.RepoDetailsArgument
import com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment.lists.StarredAdapter
import java.util.*

class StarredFragment : Fragment() {

    private var viewBinding: FragmentStarredBinding? = null
    private val viewModel: StarredViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var starredAdapter: StarredAdapter
    private lateinit var newList: List<RepoEntity>

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

        viewModel.getAllRepos().observe(viewLifecycleOwner, {
            updateRepoList(it)
            newList = it
        })

    }

    private fun setUpAdapter() {
        viewBinding?.starredRecyclerView?.layoutManager = LinearLayoutManager(activity)
        starredAdapter = StarredAdapter { repoItem ->
            mainViewModel.goToDetailsFragment(RepoDetailsArgument(repoItem.username!!, repoItem.repoName!!))
        }
        viewBinding?.starredRecyclerView?.adapter = starredAdapter
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

                updateRepoList(newList)

                val filteredList = starredAdapter.repoEntity.filter {
                    it.username!!.lowercase(Locale.getDefault()).contains(searchText)
                }
                updateRepoList(filteredList)
                return false
            }
        })
    }
}