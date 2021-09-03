package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hatidzheonbashieva.githubrepoproject.R
import com.hatidzheonbashieva.githubrepoproject.databinding.FragmentSearchBinding
import com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment.lists.SearchAdapter
import com.hatidzheonbashieva.githubrepoproject.model.Repo

class SearchFragment : Fragment(){

    private var viewBinding: FragmentSearchBinding? = null
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter

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

    }

    private fun setUpAdapter(){
        viewBinding?.searchRecyclerView?.layoutManager = LinearLayoutManager(activity)
        searchAdapter = SearchAdapter()
        viewBinding?.searchRecyclerView?.adapter = searchAdapter
    }

    private fun updateRepoList(repoList: List<Repo>){
        searchAdapter.repos = repoList as ArrayList<Repo>
        searchAdapter.notifyDataSetChanged()

    }

    private fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchMenuItem = menu?.findItem(R.id.searchRepos)
        val searchView = searchMenuItem?.actionView as SearchView

        searchView.queryHint = "Enter a username..."


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, DetailsFragment()).commit()
                viewModel.setUsername(query)
                viewModel.fetchData().observeOnce(requireActivity(), Observer<List<Repo>> { repos ->
                    updateRepoList(repos)
                })
                searchMenuItem.collapseActionView()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //viewModel.setUsername(newText)
                return false
            }

        })
    }

}