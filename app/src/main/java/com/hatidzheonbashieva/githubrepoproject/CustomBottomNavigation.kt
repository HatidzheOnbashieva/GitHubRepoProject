package com.hatidzheonbashieva.githubrepoproject

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hatidzheonbashieva.githubrepoproject.databinding.CustomBottomNavigationBinding

class CustomBottomNavigation
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BottomNavigationView(context, attrs, defStyleAttr) {

    private var viewBinding: CustomBottomNavigationBinding =
        CustomBottomNavigationBinding.inflate(LayoutInflater.from(context), this, true)


//    fun onClick(callback: () -> Unit) {
//        viewBinding.bottomNav.setOnClickListener {
//            callback.invoke()
//        }
//    }

//    fun onItemClick(): Int {
//        var value: Int = 0
//        viewBinding.bottomNav.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.searchRepos -> value = 1
//                R.id.starredRepos -> value = 2
//            }
//            true
//        }
//        return value
//    }
}