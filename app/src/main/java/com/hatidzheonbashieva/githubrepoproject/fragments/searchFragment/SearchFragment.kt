package com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.hatidzheonbashieva.githubrepoproject.R
import com.hatidzheonbashieva.githubrepoproject.databinding.FragmentSearchBinding
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.DetailsFragment
import kotlin.concurrent.fixedRateTimer

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

        Log.e("searchFragment", "onViewCreated")
        setHasOptionsMenu(true)

        //viewBinding?.toolbar?.inflateMenu(R.menu.menu_search)

//        viewBinding?.toolbar?.setOnMenuItemClickListener {
//            when (it.itemId) {
//                //R.id.searchRepos -> requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, DetailsFragment()).addToBackStack(null).commit()
//            }
//            true
//        }
//
//        <androidx.appcompat.widget.Toolbar
//        android:id="@+id/toolbar"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:background="@color/black"
//        app:layout_constraintTop_toTopOf="parent"
//        app:layout_constraintStart_toStartOf="parent"
//        app:layout_constraintEnd_toEndOf="parent"/>

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchMenuItem = menu?.findItem(R.id.searchRepos)
        val searchView = searchMenuItem?.actionView as SearchView


        searchView.queryHint = "Enter a username..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(requireContext(), "Toast message from Search Fragment", Toast.LENGTH_SHORT).show()
                if (!searchView.isIconified()) {
                    searchView.setIconified(true)
                }
                searchMenuItem.collapseActionView()
                return false
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

}