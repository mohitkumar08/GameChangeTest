package com.bit.gamechangetest.ui.issuedetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bit.gamechangetest.AppObjectController
import com.bit.gamechangetest.BaseApplication
import com.bit.gamechangetest.repository.server.CommentModel
import com.bit.gamechangetest.util.showInternetNotAvailableMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class IssueDetailViewModel(application: Application) : AndroidViewModel(application) {

    var issueCommentId: Int = 0
    var issueId: Long = 0

    private var context: BaseApplication = getApplication()
    private val _issueDetailsLiveData: MutableLiveData<List<CommentModel>> = MutableLiveData()
    private val issueDetailsLiveData: LiveData<List<CommentModel>> = _issueDetailsLiveData


    fun fetchAllCommentsOfIssue() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var commentList = getIssueFromRepository()
                if (commentList.isNullOrEmpty()) {
                    val response: List<CommentModel> = getIssueDetailsFromAPI(issueCommentId)
                    if (response.isNullOrEmpty().not()) {
                        response.forEach { model -> model.issueId = issueId }
                        AppObjectController.appDatabase.commentModelDao()
                            .insertCommentDetails(response)
                        commentList = getIssueFromRepository()
                    }
                }
                _issueDetailsLiveData.postValue(commentList)
            } catch (ex: Exception) {
                _issueDetailsLiveData.postValue(emptyList())
                ex.printStackTrace()
            }
        }
    }


    private suspend fun getIssueDetailsFromAPI(issueId: Int): List<CommentModel> {
        return try {
            AppObjectController.commonNetworkService.getIssueComments(issueId)
        } catch (ex: Exception) {
            ex.printStackTrace()
            when (ex) {
                is SocketTimeoutException, is UnknownHostException -> {
                    showInternetNotAvailableMessage(context)
                }
                else -> {
                }
            }
            emptyList()
        }
    }

    private suspend fun getIssueFromRepository(): List<CommentModel> {
        return AppObjectController.appDatabase.commentModelDao().getAllIssues(issueId)
    }

    fun getIssueObservable(): LiveData<List<CommentModel>> {
        return issueDetailsLiveData
    }


}