package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment

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
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
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
    private var isNetworkConnected: Boolean = false
    private var dialog: AlertDialog?  = null

//    companion object {
//        private var ERRORTEXT = "There is no user with such username!"
//    }

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

        getConnectivityManager().registerNetworkCallback(getNetworkRequest(), getNetworkCallBack())

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
//                    viewBinding?.errorText?.text = ERRORTEXT
                    viewModel.errorMessage.observe(viewLifecycleOwner,{ errorMessage ->
                        viewBinding?.errorText?.text = errorMessage
                    })
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


                if (isNetworkConnected) {
                    mainViewModel.showHideProgressBar(false)
                    viewModel.setUsername(newUsername)
                    searchMenuItem.collapseActionView()

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
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
//        getConnectivityManager().registerNetworkCallback(getNetworkRequest(), getNetworkCallBack())
    }
//
//    override fun onPause() {
//        super.onPause()
//        getConnectivityManager().unregisterNetworkCallback(getNetworkCallBack())
//    }


    private fun getConnectivityManager() =
        App.appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    private fun getNetworkRequest(): NetworkRequest {
        return NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    }


    private fun getNetworkCallBack(): ConnectivityManager.NetworkCallback {
        return object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                //Toast.makeText(App.appContext, "Internet is on!", Toast.LENGTH_SHORT).show()
                isNetworkConnected = true
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                //Toast.makeText(App.appContext, "Internet is off!", Toast.LENGTH_SHORT).show()
                isNetworkConnected = false
            }
        }
    }
}