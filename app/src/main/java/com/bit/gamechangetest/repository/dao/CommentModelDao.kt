package com.bit.gamechangetest.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bit.gamechangetest.repository.server.CommentModel

@Dao
interface CommentModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommentDetails(issueList: List<CommentModel>)

    @Query(value = "SELECT * FROM comment_table  WHERE issue_id= :id  ORDER BY updated_at DESC")
    suspend fun getAllIssues(id:Long): List<CommentModel>


}