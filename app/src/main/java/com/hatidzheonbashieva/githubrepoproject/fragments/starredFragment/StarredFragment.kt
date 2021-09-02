package com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.hatidzheonbashieva.githubrepoproject.R
import com.hatidzheonbashieva.githubrepoproject.databinding.FragmentStarredBinding

class StarredFragment  : Fragment() {

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

        setHasOptionsMenu(true)
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