package com.hatidzheonbashieva.githubrepoproject.fragments.detailsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.hatidzheonbashieva.githubrepoproject.R
import com.hatidzheonbashieva.githubrepoproject.databinding.FragmentDetailsBinding
import com.hatidzheonbashieva.githubrepoproject.fragments.searchFragment.SearchFragment
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {

    private var viewBinding: FragmentDetailsBinding? = null
    private var favouriteFlag: Boolean = false

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
        var username:String? = this.requireArguments().getString("username")
        var avatarUrl: String? = this.requireArguments().getString("avatarUrl")
        var description:String? = this.requireArguments().getString("description")
        var language: String? = this.requireArguments().getString("language")
        var dateCreated:String? = this.requireArguments().getString("dateCreated")
        var url:String? = this.requireArguments().getString("url")


        Picasso.get().load(avatarUrl).into(viewBinding?.profileImage)
        viewBinding?.username?.text = username
        viewBinding?.repoName?.text = repoName
        viewBinding?.description?.text = description
        viewBinding?.language?.text = language
        viewBinding?.dateCreated?.text = dateCreated
        viewBinding?.url?.setOnClickListener{
            //follow the url
        }
        goBack()
        saveDataToDb()

      }


    private fun goBack(){
        viewBinding?.back?.setOnClickListener{
//            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
//            transaction.replace(R.id.fragment_container, SearchFragment()).commit()
            parentFragmentManager.popBackStack()
        }
    }

    private fun saveDataToDb(){
        viewBinding?.favourite?.setOnClickListener{

            favouriteFlag = if(favouriteFlag){
                viewBinding?.favourite?.setImageResource(R.drawable.ic_star_no_fill)
                false
            } else{
                viewBinding?.favourite?.setImageResource(R.drawable.ic_filled_star)
                true
            }
        }
    }
}