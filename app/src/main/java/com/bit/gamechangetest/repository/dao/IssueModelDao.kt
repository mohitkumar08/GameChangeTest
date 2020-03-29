package com.bit.gamechangetest.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bit.gamechangetest.repository.server.IssueModel


@Dao
interface IssueModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIssueList(issueList: List<IssueModel>)

    @Query(value = "SELECT * FROM issue_table ORDER BY updated_at DESC ")
    suspend fun getAllIssues(): List<IssueModel>


}