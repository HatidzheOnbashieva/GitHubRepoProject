package com.hatidzheonbashieva.githubrepoproject

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.hatidzheonbashieva.githubrepoproject.databinding.ActivityMainBinding
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.DetailsFragment
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.RepoDetailsArgument
import com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment.SearchFragment
import com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment.StarredFragment

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        if (supportFragmentManager.fragments.isEmpty()) {
            replaceFragment(SearchFragment())
        }

        val customBottomNavigation = CustomBottomNavigation(this)

//        when(customBottomNavigation.onItemClick()){
//            1 -> replaceFragment(SearchFragment())
//            2 -> replaceFragment(StarredFragment())
//        }

//        customBottomNavigation.onItemClick {
//          when(it){
//              1 -> replaceFragment(SearchFragment())
//              2 -> replaceFragment(StarredFragment())
//          }
//        }

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

        mainViewModel.loader.observe(this, { state ->
            if(state){
                hideProgressBar()
            }
            else{
                showProgressBar()
            }
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

    private fun showProgressBar() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = this.layoutInflater
        builder.setView(inflater.inflate(R.layout.loading_dialog, null))
        builder.setCancelable(true)

        dialog = builder.create()
        dialog?.show()
    }

    private fun hideProgressBar() {
        dialog?.dismiss()
    }
}

