package com.bit.gamechangetest.ui.issuelist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bit.gamechangetest.R
import com.bit.gamechangetest.databinding.ActivityIssueListBinding
import com.bit.gamechangetest.repository.server.IssueModel
import com.bit.gamechangetest.ui.issuedetail.IssueDetailsActivity
import com.bit.gamechangetest.util.MarginItemDecoration
import com.bit.gamechangetest.util.isInternetAvailable


class IssueListActivity : AppCompatActivity(), OnIssueInteractionListener {
    private val viewModel: IssueViewModel by lazy {
        ViewModelProvider(this).get(IssueViewModel::class.java)
    }
    private lateinit var binding: ActivityIssueListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_issue_list)
        initObservable()
        initRecyclerView()
        if (isInternetAvailable()) {
            viewModel.fetchIssues()
        } else {
            showInternetNotAvailable()
            viewModel.fetchFromLocalIfAvailable()
        }
    }

    private fun initRecyclerView() {
        val mLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(applicationContext)
        binding.recyclerView.layoutManager = mLayoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.setHasFixedSize(false)
        binding.recyclerView.addItemDecoration(
            MarginItemDecoration(resources.getDimension(R.dimen.dp8).toInt())
        )
    }

    private fun initObservable() {
        viewModel.getIssueObservable().observe(this, Observer {
            binding.progressBar.visibility = View.GONE
            if (it.isNullOrEmpty()) {
                return@Observer
            }
            binding.recyclerView.adapter = IssueAdapter(this, it)
        })
    }

    override fun onClickIssue(issueModel: IssueModel) {
        IssueDetailsActivity.openCommentDetailsActivity(
            this,
            issueModel.title,
            issueModel.issueId,
            issueModel.number
        )
    }

    private fun showInternetNotAvailable() {
        Toast.makeText(this, getString(R.string.internet_not_available_msz), Toast.LENGTH_LONG)
            .show()
    }
}