package com.hatidzheonbashieva.githubrepoproject.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class User(
    @SerializedName("id") var _id: Int?,
    @SerializedName("login") var _username: String?,
    @SerializedName("avatar_url") var _avatarUrl: String?
) : Serializable {

    val id
        get() = _id ?: 0
    val username
        get() = _username ?: ""

    val avatarUrl
        get() = _avatarUrl ?: ""
}
