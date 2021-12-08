package com.hatidzheonbashieva.githubrepoproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment.RepoDetailsArgument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val arguments: MutableSharedFlow<RepoDetailsArgument> = MutableSharedFlow()
    val loader: MutableSharedFlow<Boolean> = MutableSharedFlow()

    fun goToDetailsFragment(argument: RepoDetailsArgument) {
        viewModelScope.launch {
            arguments.emit(argument)
        }
    }

    fun showHideProgressBar(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            loader.emit(state)
        }
    }
}