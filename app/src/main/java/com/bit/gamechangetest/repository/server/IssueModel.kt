package com.bit.gamechangetest.repository.server


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "issue_table")
data class IssueModel(
    @PrimaryKey
    @ColumnInfo(name = "issue_id")
    @SerializedName("id")
    val issueId: Long,

    @ColumnInfo
    @SerializedName("body")
    val body: String,

    @ColumnInfo
    @SerializedName("title")
    val title: String,

    @ColumnInfo
    @SerializedName("number")
    val number: Int,

    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    val createdAt: Date,

    @ColumnInfo(name = "updated_at")
    @SerializedName("updated_at")
    val updatedAt: Date
)