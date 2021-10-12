package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hatidzheonbashieva.githubrepoproject.App
import com.hatidzheonbashieva.githubrepoproject.MainViewModel
import com.hatidzheonbashieva.githubrepoproject.R
import com.hatidzheonbashieva.githubrepoproject.databinding.FragmentSearchBinding
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.RepoDetailsArgument
import com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment.lists.SearchAdapter
import com.hatidzheonbashieva.githubrepoproject.model.Repos
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var viewBinding: FragmentSearchBinding? = null
    private val viewModel: SearchViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var searchAdapter: SearchAdapter
    private var settingsFlag = false
    private var isNetworkConnected: Boolean = false

    companion object {
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

//        if (!isConnected()) {
//                val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
//                builder
//                    .setTitle("NO INTERNET ACCESS")
//                    .setMessage("Please connect to a wi-fi...")
//                    .setCancelable(false)
//                    .setPositiveButton("Connect") { dialog, id ->
//                        startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
//                        settingsFlag = true
//
//                    }
//                    .setNegativeButton("Cancel") { dialog, id ->
//                        requireActivity().finish()
//                    }
//                    .create()
//                    .show()
//        }


        viewModel.userRepos.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                mainViewModel.showHideProgressBar(true)
                viewBinding?.errorText?.visibility = View.INVISIBLE
                viewBinding?.searchRecyclerView?.visibility = View.VISIBLE
                updateRepoList(it)

            } else {
                lifecycleScope.launch {
                    delay(3000)
                    mainViewModel.showHideProgressBar(true)
                    viewBinding?.searchRecyclerView?.visibility = View.INVISIBLE
                    viewBinding?.errorText?.visibility = View.VISIBLE
                    viewBinding?.errorText?.text = ERRORTEXT
                }
            }
        })
    }

    private fun setUpAdapter() {
        viewBinding?.searchRecyclerView?.layoutManager = LinearLayoutManager(activity)
        searchAdapter = SearchAdapter { repoItem ->
            mainViewModel.goToDetailsFragment(
                RepoDetailsArgument(
                    repoItem.users.username,
                    repoItem.repoName
                )
            )
        }
        viewBinding?.searchRecyclerView?.adapter = searchAdapter
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

                if (isConnected()) {
                    mainViewModel.showHideProgressBar(false)
                }
                else {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("NO INTERNET ACCESS")
                    builder.setMessage("Please check your internet connectivity!")
                    builder.setCancelable(false)
                    builder.create()
                    builder.show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

//    override fun onResume() {
//        super.onResume()
//        if(settingsFlag){
//            parentFragmentManager.beginTransaction().replace(R.id.fragment_container, SearchFragment())
//                .commit()
//        }
//    }

    private fun isConnected(): Boolean {
        try {
            val connectivityManager =
                App.appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork

            if (connectivityManager.getNetworkCapabilities(network)!!
                    .hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || connectivityManager.getNetworkCapabilities(network)!!
                    .hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            ) {
                return true
            }
            return false
        } catch (e: Exception) {
            println("Error from catch in isConnected() method ${e.message}")
            return false
        }
    }
}