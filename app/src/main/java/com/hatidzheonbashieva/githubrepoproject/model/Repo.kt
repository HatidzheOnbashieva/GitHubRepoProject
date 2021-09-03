package com.hatidzheonbashieva.githubrepoproject.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Repo(@SerializedName("id") var _id: Int?, @SerializedName("name") var _repoName: String?, @SerializedName("owner") var _users: Owner?) : Serializable {

    val id
        get() = _id ?: 0

    val repoName
        get() = _repoName ?: ""

    val users
        get() = _users

    init {
        this.id
        this.repoName
        this.users
    }
}