package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hatidzheonbashieva.githubrepoproject.R
import com.hatidzheonbashieva.githubrepoproject.databinding.FragmentSearchBinding
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.DetailsFragment
import com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment.lists.SearchAdapter
import com.hatidzheonbashieva.githubrepoproject.model.Repos

class SearchFragment : Fragment() {

    private var viewBinding: FragmentSearchBinding? = null
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter
    private var newUsername: String = ""

    companion object{
        private var ERRORTEXT = "There is no user with such username!"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return viewBinding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        viewBinding?.toolbar?.title = "Search for a user"
        (activity as AppCompatActivity).setSupportActionBar(viewBinding?.toolbar)
        setHasOptionsMenu(true)

        //viewModel.getUsername()

        viewModel.userRepoList.observe(requireActivity(), Observer {
            if (it.isNotEmpty()) {
                viewBinding?.errorText?.visibility = View.INVISIBLE
                viewBinding?.searchRecyclerView?.visibility = View.VISIBLE
                updateRepoList(it)
            } else {
                viewBinding?.searchRecyclerView?.visibility = View.INVISIBLE
                viewBinding?.errorText?.visibility = View.VISIBLE
                viewBinding?.errorText?.text = ERRORTEXT
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.cancelJobs()
    }

    private fun setUpAdapter() {
        viewBinding?.searchRecyclerView?.layoutManager = LinearLayoutManager(activity)
        searchAdapter = SearchAdapter { repoItem ->
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

        val searchMenuItem = menu.findItem(R.id.searchRepos)
        val searchView = searchMenuItem?.actionView as SearchView

        searchView.queryHint = "Enter a username..."


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                newUsername = query.replace(" ", "")
                searchMenuItem.collapseActionView()
                call()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
    }

    private fun call() {
        viewModel.setUsername(username = newUsername)

        viewModel.userRepoList.observe(requireActivity(), Observer {
            if (it.isNotEmpty()) {
                println(it) //delete this one later
                viewBinding?.errorText?.visibility = View.INVISIBLE
                viewBinding?.searchRecyclerView?.visibility = View.VISIBLE
                updateRepoList(it)
            } else {
                viewBinding?.searchRecyclerView?.visibility = View.INVISIBLE
                viewBinding?.errorText?.visibility = View.VISIBLE
                viewBinding?.errorText?.text = ERRORTEXT
            }
        })
    }
}