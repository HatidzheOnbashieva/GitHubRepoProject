package com.hatidzheonbashieva.githubrepoproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.hatidzheonbashieva.githubrepoproject.databinding.ActivityMainBinding
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.DetailsFragment
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.RepoDetailsArgument
import com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment.SearchFragment
import com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment.StarredFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
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


        viewBinding.customBottomNav.onClick {
            when(it.itemId){
                R.id.searchRepos -> replaceFragment(SearchFragment())
                R.id.starredRepos -> replaceFragment(StarredFragment())
            }
        }

        lifecycleScope.launch {
            mainViewModel.arguments.collect { argument ->
                goToDetailsFragment(argument)
            }
        }

        lifecycleScope.launch{
            mainViewModel.loader.collect{ state ->
                if (state) {
                    hideProgressBar()
                } else {
                    showProgressBar()
                }
            }
        }
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
        builder.setCancelable(false)

        dialog = builder.create()
        dialog?.show()
    }

    private fun hideProgressBar() {
        dialog?.dismiss()
    }
}

