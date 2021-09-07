package com.hatidzheonbashieva.githubrepoproject.model

import com.google.gson.annotations.SerializedName

data class Repos(
    @SerializedName("id") var _id: Int?,
    @SerializedName("name") var _repoName: String?,
    @SerializedName("owner") var _users: Owners?,
    @SerializedName("description") var _description: String?,
    @SerializedName("language") var _language: String?,
    @SerializedName("created_at") var _dateCreated: String?,
    @SerializedName("url") var _repoUrl: String?
){

    val id
        get() = _id ?: 0

    val repoName
        get() = _repoName ?: ""

    val users
        get() = _users

    val description
        get() = _description ?: ""

    val language
        get() = _language ?:""

    val dateCreated
        get() = _dateCreated ?: ""

    val url
        get() = _repoUrl ?: ""



    init {
        this.id
        this.repoName
        this.users
        this.description
        this.language
        this.dateCreated
        this.url
    }
}