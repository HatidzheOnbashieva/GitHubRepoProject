package com.hatidzheonbashieva.githubrepoproject

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.constraintlayout.widget.ConstraintLayout
import com.hatidzheonbashieva.githubrepoproject.databinding.CustomBottomNavigationBinding

class CustomBottomNavigation
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    private var viewBinding: CustomBottomNavigationBinding =
        CustomBottomNavigationBinding.inflate(LayoutInflater.from(context), this, true)

    fun onClick(callback: (MenuItem) -> Unit) {
        viewBinding.bottomNav.setOnItemSelectedListener {
            callback.invoke(it)
            true
        }
    }
}