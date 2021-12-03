package com.hatidzheonbashieva.githubrepoproject.fragments.starredFragment

import androidx.lifecycle.ViewModel
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity
import com.hatidzheonbashieva.githubrepoproject.searchRepository.StarredRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class StarredViewModel @Inject constructor(
    private val starredRepository: StarredRepository
) : ViewModel() {

    fun getAllRepos(): Flow<List<RepoEntity>> = starredRepository.getAllRepos()

}
