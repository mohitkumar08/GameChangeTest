package com.bit.gamechangetest.ui.issuelist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bit.gamechangetest.AppObjectController
import com.bit.gamechangetest.BaseApplication
import com.bit.gamechangetest.repository.internal.LAST_SYNC_TIME
import com.bit.gamechangetest.repository.internal.PrefManager
import com.bit.gamechangetest.repository.server.IssueModel
import com.bit.gamechangetest.util.isTimeIsGreaterThen24Hours
import com.bit.gamechangetest.util.showInternetNotAvailableMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*

class IssueViewModel(application: Application) : AndroidViewModel(application) {

    private var context: BaseApplication = getApplication()
    private val _issueLiveData: MutableLiveData<List<IssueModel>> = MutableLiveData()
    private val issueLiveData: LiveData<List<IssueModel>> = _issueLiveData

    fun fetchIssues() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var issueList = getIssueFromRepository()
                if (isTimeIsGreaterThen24Hours(PrefManager.getLongValue(LAST_SYNC_TIME)) || issueList.isNullOrEmpty()) {
                    AppObjectController.appDatabase.clearDatabase()
                    val response: List<IssueModel> = getIssueFromAPI()
                    if (response.isNullOrEmpty().not()) {
                        AppObjectController.appDatabase.issueDao().insertIssueList(response)
                        PrefManager.put(LAST_SYNC_TIME, Date().time)
                        issueList = getIssueFromRepository()
                    }
                }
                _issueLiveData.postValue(issueList)
            } catch (ex: Exception) {
                _issueLiveData.postValue(emptyList())
                ex.printStackTrace()
            }
        }
    }

    private suspend fun getIssueFromAPI(): List<IssueModel> {
        return try {
            AppObjectController.commonNetworkService.getAllIssues()
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

    private suspend fun getIssueFromRepository(): List<IssueModel> {
        return AppObjectController.appDatabase.issueDao().getAllIssues()
    }

    fun getIssueObservable(): LiveData<List<IssueModel>> {
        return issueLiveData
    }

}