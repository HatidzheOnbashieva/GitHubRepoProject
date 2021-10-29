package com.hatidzheonbashieva.githubrepoproject

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import com.hatidzheonbashieva.githubrepoproject.databinding.CustomSearchBarBinding

class CustomSearchBar @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    Toolbar(context, attrs, defStyleAttr) {

    private var viewBinding: CustomSearchBarBinding =
        CustomSearchBarBinding.inflate(LayoutInflater.from(context), this, true)

    fun setSearchBar(){
        viewBinding.toolbar.title = "Search for a user"
    }
}