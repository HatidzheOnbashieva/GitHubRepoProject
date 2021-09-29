package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hatidzheonbashieva.githubrepoproject.MainViewModel
import com.hatidzheonbashieva.githubrepoproject.R
import com.hatidzheonbashieva.githubrepoproject.databinding.FragmentSearchBinding
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.RepoDetailsArgument
import com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment.lists.SearchAdapter
import com.hatidzheonbashieva.githubrepoproject.model.Repos

class SearchFragment : Fragment() {

    private lateinit var viewBinding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var searchAdapter: SearchAdapter

    companion object {
        private var ERRORTEXT = "There is no user with such username!"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        viewBinding.toolbar.title = "Search for a user"
        (activity as AppCompatActivity).setSupportActionBar(viewBinding.toolbar)
        setHasOptionsMenu(true)

        viewModel.userRepos.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                viewBinding.errorText.visibility = View.INVISIBLE
                viewBinding.searchRecyclerView.visibility = View.VISIBLE
                updateRepoList(it)
            } else {
                viewBinding.searchRecyclerView.visibility = View.INVISIBLE
                viewBinding.errorText.visibility = View.VISIBLE
                viewBinding.errorText.text = ERRORTEXT
            }
        })
    }

    private fun setUpAdapter() {
        viewBinding.searchRecyclerView.layoutManager = LinearLayoutManager(activity)
        searchAdapter = SearchAdapter { repoItem ->
            mainViewModel.goToDetailsFragment(RepoDetailsArgument(repoItem.users.username, repoItem.repoName))
        }
        viewBinding.searchRecyclerView.adapter = searchAdapter
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun updateRepoList(repoList: List<Repos>) {
        searchAdapter.repos = repoList as ArrayList<Repos>
        searchAdapter.notifyDataSetChanged()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //inflating the menu
        inflater.inflate(R.menu.menu_search, menu)
        //initialize menu item
        val searchMenuItem = menu.findItem(R.id.searchRepos)
        //initialize searchView
        val searchView = searchMenuItem?.actionView as SearchView

        searchView.queryHint = "Enter a username..."


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val newUsername = query.replace(" ", "")
                viewModel.setUsername(newUsername)
                searchMenuItem.collapseActionView()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }
}