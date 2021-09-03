package com.hatidzheonbashieva.githubrepoproject.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Owner(
    @SerializedName("login") var _username: String?,
    @SerializedName("avatar_url") var _avatarUrl: String?
) : Serializable {


    val username
        get() = _username ?: ""

    val avatarUrl
        get() = _avatarUrl ?: ""

    init {
        this.username
        this.avatarUrl
    }
}
