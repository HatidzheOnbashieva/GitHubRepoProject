package com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.hatidzheonbashieva.githubrepoproject.R
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity
import com.hatidzheonbashieva.githubrepoproject.databinding.FragmentDetailsBinding
import com.hatidzheonbashieva.githubrepoproject.model.Repos
import com.hatidzheonbashieva.githubrepoproject.utils.trimDate
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var viewBinding: FragmentDetailsBinding? = null
    private lateinit var repoEntity: RepoEntity
    private var favouriteFlag: Boolean = false
    private val viewModel: DetailsViewModel by viewModels()
    private var repoId: Int = 0
    private var repoURL: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentDetailsBinding.inflate(layoutInflater, container, false)

        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            lifecycleScope.launch {
                viewModel.arguments.collect { repo ->
//                    newRepoId = repoId
//                    if (repo.id == 0) {
//                        viewBinding?.errorText?.visibility = View.VISIBLE
//                        viewBinding?.errorText?.text = error
//
//                    } else {
//                        repoEntity = RepoEntity(
//                            repo.id,
//                            repo.repoName,
//                            repo.users.username,
//                            repo.users.avatarUrl,
//                            repo.description,
//                            repo.language,
//                            repo.dateCreated,
//                            repo.url
//                        )
//                    }

//                    lifecycleScope.launch {
//                        viewModel.getRepoId(repo.id).collect { state ->
//                            if (state) {
//                                favouriteFlag = true
//                                viewBinding?.favourite?.setImageResource(R.drawable.ic_filled_star)
//                            }
//                        }
//                    }

                    repoEntity = RepoEntity(
                        repo.id,
                        repo.repoName,
                        repo.users.username,
                        repo.users.avatarUrl,
                        repo.description,
                        repo.language,
                        repo.dateCreated,
                        repo.url
                    )

                    Picasso.get().load(repo.users.avatarUrl).into(viewBinding?.profileImage)
                    viewBinding?.username?.text = repo.users.username
                    viewBinding?.repoName?.text = repo.repoName
                    viewBinding?.description?.text = repo.description
                    viewBinding?.language?.text = repo.language
                    val newDate = trimDate(repo.dateCreated)
                    viewBinding?.dateCreated?.text = newDate.toString()
//
//                    viewModel.errorMessage.collect { errorMessage ->
//                        viewBinding?.errorText?.visibility = View.VISIBLE
//                        viewBinding?.errorText?.text = errorMessage
//                    }
//

                    //
//                    viewModel.execute {
//                        val state = viewModel.getRepoId(repo.id)
//                        if(state){
//                            favouriteFlag = true
//                            viewBinding?.favourite?.setImageResource(R.drawable.ic_filled_star)
//                        }
//                    }

                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.errorMessage.collect { errorMessage ->
                        viewBinding?.errorText?.visibility = View.VISIBLE
                        viewBinding?.errorText?.text = errorMessage
                    }
                    }

                    CoroutineScope(Dispatchers.IO).launch {
                        val state = viewModel.getRepoId(repo.id)
                        if(state){
                            favouriteFlag = true
                            viewBinding?.favourite?.setImageResource(R.drawable.ic_filled_star)
                        }
                    }


                }
            }

        }

        viewBinding?.url?.setOnClickListener {
           openUserRepo(repoURL)
        }

        viewBinding?.back?.setOnClickListener {
            goBack()
        }

        viewBinding?.favourite?.setOnClickListener {
            favouriteFlag = if (favouriteFlag) {
                viewModel.deleteRepoId(repoId)
                viewBinding!!.favourite.setImageResource(R.drawable.ic_star_no_fill)
                false
            } else {
                viewModel.addRepo(repoEntity)
                viewBinding!!.favourite.setImageResource(R.drawable.ic_filled_star)
                true
            }
        }
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

    private fun openUserRepo(repoURL: String) {
        if(repoURL.isNotEmpty()) {
            val followUrl = Intent(Intent.ACTION_VIEW, Uri.parse(repoURL))
            startActivity(followUrl)
        }
    }
}