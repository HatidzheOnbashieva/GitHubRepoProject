package com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hatidzheonbashieva.githubrepoproject.App
import com.hatidzheonbashieva.githubrepoproject.MainViewModel
import com.hatidzheonbashieva.githubrepoproject.R
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity
import com.hatidzheonbashieva.githubrepoproject.databinding.FragmentStarredBinding
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.DetailsViewModel
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.RepoDetailsArgument
import com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment.lists.StarredAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class StarredFragment : Fragment() {

    private var viewBinding: FragmentStarredBinding? = null
    private val viewModel: StarredViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var starredAdapter: StarredAdapter
    private lateinit var newList: List<RepoEntity>
    private var isNetworkConnected: Boolean = false
    private var dialog: AlertDialog? = null

    @Inject
    lateinit var appContext: Context

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

        viewBinding?.customSearchBar?.title = "Search for a user"
        (activity as AppCompatActivity).setSupportActionBar(viewBinding?.customSearchBar)
        setHasOptionsMenu(true)

        registerNetworkCallback()

        viewModel.getAllRepos().observe(viewLifecycleOwner, {
            updateRepoList(it)
            newList = it
        })

    }

    private fun setUpAdapter() {
        viewBinding?.starredRecyclerView?.layoutManager = LinearLayoutManager(activity)
        starredAdapter = StarredAdapter { repoItem ->

            if (isNetworkConnected) {
                mainViewModel.goToDetailsFragment(RepoDetailsArgument(repoItem.username!!, repoItem.repoName!!))

                lifecycleScope.launch {
                    mainViewModel.showHideProgressBar(false)
                    delay(1000)
                    mainViewModel.showHideProgressBar(true)
                }

            } else {
                val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
                builder.setTitle("NO INTERNET ACCESS")
                    .setMessage("Please check your internet connectivity!")
                    .setCancelable(false)
                    .setPositiveButton("Connect") { _, _ ->
                        startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.cancel()
                    }
                dialog = builder.create()
                dialog?.show()

            }
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


    private fun registerNetworkCallback(){
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val builder = NetworkRequest.Builder()
        builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

        val networkRequest = builder.build()
        connectivityManager.registerNetworkCallback(networkRequest,
            object : ConnectivityManager.NetworkCallback () {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    isNetworkConnected = true
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    isNetworkConnected = false
                }
            })
    }
}