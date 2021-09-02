package com.hatidzheonbashieva.githubrepoproject.model

import com.google.gson.annotations.SerializedName

data class Users( @SerializedName("owner") var _users: List<User> ) {

    val users
        get() = _users

    init{
        this.users
    }
}