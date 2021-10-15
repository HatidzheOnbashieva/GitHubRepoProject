package com.hatidzheonbashieva.githubrepoproject

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.hatidzheonbashieva.githubrepoproject.databinding.CustomItemLayoutBinding
import com.squareup.picasso.Picasso

class CustomItemLayout
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    private var viewBinding: CustomItemLayoutBinding =
        CustomItemLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    fun displayName(name: String) {
        viewBinding.repoName.text = name
    }

    fun displayImage(url: String) {
        Picasso.get().load(url).into(viewBinding.profileImage)
    }

    fun displayUserName(username: String) {
        viewBinding.username.text = username
    }
}