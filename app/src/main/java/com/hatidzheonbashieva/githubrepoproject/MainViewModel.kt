package com.hatidzheonbashieva.githubrepoproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.RepoDetailsArgument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val arguments: MutableLiveData<RepoDetailsArgument> = MutableLiveData()
    val loader: MutableLiveData<Boolean> = MutableLiveData()
    val isConnected: MutableLiveData<Boolean> = MutableLiveData()


    fun goToDetailsFragment(argument: RepoDetailsArgument) {
        arguments.postValue(argument)
    }

    fun showHideProgressBar(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            loader.postValue(state)
        }
    }
//
//    fun internetConnectivityStatus():Boolean{
//        if(isConnected.value == true){
//            return true
//        }else{
//            return false
//        }
//
//    }
}