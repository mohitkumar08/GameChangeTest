package com.bit.gamechangetest.repository.service

import com.bit.gamechangetest.repository.server.CommentModel
import com.bit.gamechangetest.repository.server.IssueModel
import retrofit2.http.*

const val DIR = "repos/firebase/firebase-ios-sdk"

interface CommonNetworkService {

    @GET("$DIR/issues")
    suspend fun getAllIssues(): List<IssueModel>

    @GET("$DIR/issues/{issueId}/comments")
    suspend fun getIssueComments(@Path("issueId") issueId: Int) : List<CommentModel>

}