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
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {

    private var viewBinding: FragmentDetailsBinding? = null
    private var favouriteFlag: Boolean = false
    private val viewModel: DetailsViewModel by viewModels()

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

        var repoId: Int = this.requireArguments().getInt("repoId")
        var repoName: String? = this.requireArguments().getString("repoName")
        var username: String? = this.requireArguments().getString("username")
        var avatarUrl: String? = this.requireArguments().getString("avatarUrl")
        var description: String? = this.requireArguments().getString("description")
        var language: String? = this.requireArguments().getString("language")
        var dateCreated: String? = this.requireArguments().getString("dateCreated")
        var url: String? = this.requireArguments().getString("url")


        Picasso.get().load(avatarUrl).into(viewBinding?.profileImage)
        viewBinding?.username?.text = username
        viewBinding?.repoName?.text = repoName
        viewBinding?.description?.text = description
        viewBinding?.language?.text = language
        viewBinding?.dateCreated?.text = dateCreated
        viewBinding?.url?.setOnClickListener {
            val followUrl = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(followUrl)
        }

        viewModel.getRepoId(repoId).observe(viewLifecycleOwner, {
            if (it) {
                favouriteFlag = true
                viewBinding?.favourite?.setImageResource(R.drawable.ic_filled_star)
            }
        })


        goBack()

        viewBinding?.favourite?.setOnClickListener {
            val repo = RepoEntity(
                repoId,
                repoName,
                username,
                avatarUrl,
                description,
                language,
                dateCreated,
                url
            )

            favouriteFlag = if (favouriteFlag) {
                viewModel.deleteRepoId(repoId)
                viewBinding?.favourite?.setImageResource(R.drawable.ic_star_no_fill)
                false
            } else {
                viewModel.addRepo(repo)
                viewBinding?.favourite?.setImageResource(R.drawable.ic_filled_star)
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