package com.hatidzheonbashieva.githubrepoproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.RepoDetailsArgument

class MainViewModel: ViewModel() {

    val arguments: MutableLiveData<RepoDetailsArgument> = MutableLiveData()

    fun goToDetailsFragment(argument: RepoDetailsArgument){
        arguments.postValue(argument)
    }

}