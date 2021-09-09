package com.hatidzheonbashieva.githubrepoproject.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="repository")
data class RepoEntity(
    @PrimaryKey var repoId: Int,

    @ColumnInfo(name = "repoName") var repoName: String,
    @ColumnInfo(name = "username") var username: String,
    @ColumnInfo(name = "avatarUrl") var avatarUrl: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "language") var language: String,
    @ColumnInfo(name = "dateCreated") var dateCreated: String,
    @ColumnInfo(name = "url") var url: String

)
