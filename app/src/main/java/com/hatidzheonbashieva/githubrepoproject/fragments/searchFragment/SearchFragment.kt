package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.hatidzheonbashieva.githubrepoproject.R
import com.hatidzheonbashieva.githubrepoproject.databinding.FragmentSearchBinding
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.DetailsFragment

class SearchFragment : Fragment(){

    private var viewBinding: FragmentSearchBinding? = null
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

        viewBinding?.toolbar?.title = "Search for a user"
        (activity as AppCompatActivity).setSupportActionBar(viewBinding?.toolbar)
        setHasOptionsMenu(true)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchMenuItem = menu?.findItem(R.id.searchRepos)
        val searchView = searchMenuItem?.actionView as SearchView

        searchView.queryHint = "Enter a username..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, DetailsFragment()).commit()

                searchMenuItem.collapseActionView()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

}