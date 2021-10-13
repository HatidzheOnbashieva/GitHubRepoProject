package com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hatidzheonbashieva.githubrepoproject.R
import com.hatidzheonbashieva.githubrepoproject.database.RepoEntity
import com.hatidzheonbashieva.githubrepoproject.databinding.FragmentDetailsBinding
import com.hatidzheonbashieva.githubrepoproject.utils.trimDate
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {

    private var viewBinding: FragmentDetailsBinding? = null
    private lateinit var repoEntity: RepoEntity
    private var favouriteFlag: Boolean = false
    private val viewModel: DetailsViewModel by viewModels()
    private var repoId: Int = 0

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

        viewModel.arguments.observe(viewLifecycleOwner, { repo ->
            repoEntity = RepoEntity(
                    repo.id,
                    repo.repoName,
                    repo.users.username,
                    repo.users.avatarUrl,
                    repo.description,
                    repo.language,
                    repo.dateCreated,
                    repo.url)

            //repoId = repo.id
            viewModel.getRepoId(repo.id).observe(viewLifecycleOwner, {
                if (it) {
                    favouriteFlag = true
                    viewBinding?.favourite?.setImageResource(R.drawable.ic_filled_star)
                }
            })

            Picasso.get().load(repo.users.avatarUrl).into(viewBinding?.profileImage)
            viewBinding?.username?.text = repo.users.username
            viewBinding?.repoName?.text = repo.repoName
            viewBinding?.description?.text = repo.description
            viewBinding?.language?.text = repo.language
            val newDate = trimDate(repo.dateCreated)
            viewBinding?.dateCreated?.text = newDate.toString()
            viewBinding?.url?.setOnClickListener {
                val followUrl = Intent(Intent.ACTION_VIEW, Uri.parse(repo.url))
                startActivity(followUrl)
            }
        })

        goBack()

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
        viewBinding?.back?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}