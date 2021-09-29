package com.hatidzheonbashieva.githubrepoproject

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity
import com.hatidzheonbashieva.githubrepoproject.databinding.ActivityMainBinding
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.DetailsFragment
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.RepoDetailsArgument
import com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment.SearchFragment
import com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment.StarredFragment

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        if (supportFragmentManager.fragments.isEmpty()) {
            replaceFragment(SearchFragment())
        }

        viewBinding.bottomNav.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.searchRepos -> replaceFragment(SearchFragment())
                R.id.starredRepos -> replaceFragment(StarredFragment())
            }
            true
        }

        mainViewModel.arguments.observe(this, {
            goToDetailsFragment(it)
        })

    }

    private fun goToDetailsFragment(argument: RepoDetailsArgument) {
        val fragment = DetailsFragment()
        val bundle = Bundle()
        bundle.putSerializable("argument", argument)
        fragment.arguments = bundle

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment).addToBackStack("null")
                .commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }
}

