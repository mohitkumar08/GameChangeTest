package com.bit.gamechangetest.repository.server


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "comment_table")
data class CommentModel(
    @PrimaryKey
    @ColumnInfo(name = "comment_id")
    @SerializedName("id")
    val id: Int,

    @ColumnInfo
    @SerializedName("body")
    val body: String,

    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    val createdAt: Date,

    @ColumnInfo(name = "updated_at")
    @SerializedName("updated_at")
    val updatedAt: Date,

    @Embedded
    @SerializedName("user")
    val user: User,

    @ColumnInfo(name = "issue_id")
    var issueId: Long

)


data class User(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("type")
    val type: String
)