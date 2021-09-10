package com.hatidzheonbashieva.githubrepoproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hatidzheonbashieva.githubrepoproject.databinding.ActivityMainBinding
import com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment.SearchFragment
import com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment.StarredFragment

class MainActivity : AppCompatActivity() {
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
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }
}

