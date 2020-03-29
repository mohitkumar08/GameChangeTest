package com.bit.gamechangetest.ui.issuedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bit.gamechangetest.R
import com.bit.gamechangetest.databinding.ActivityIssueDetailsBinding
import java.lang.ref.WeakReference


const val ISSUE_COMMENT_ID_OBJECT = "issue_comment_id"
const val ISSUE_ID_OBJECT = "issue_id"
const val ISSUE_TITLE_OBJECT = "issue_title_id"


class IssueDetailsActivity : AppCompatActivity() {

    private val viewModel: IssueDetailViewModel by lazy {
        ViewModelProvider(this).get(IssueDetailViewModel::class.java)
    }
    private lateinit var binding: ActivityIssueDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_issue_details)
        handleIntent()
        setSupportToolbar()
        initObservable()
        initRecyclerView()
        viewModel.fetchAllCommentsOfIssue()
    }

    private fun setSupportToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        intent.getStringExtra(ISSUE_TITLE_OBJECT)?.run {
            supportActionBar?.title = this
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        this.finish()
        return true
    }

    private fun handleIntent() {
        val issueCommentId = intent.getIntExtra(ISSUE_COMMENT_ID_OBJECT, -1)
        val issueId = intent.getLongExtra(ISSUE_ID_OBJECT, -1)
        viewModel.issueCommentId = issueCommentId
        viewModel.issueId = issueId
    }

    private fun initRecyclerView() {
        val mLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(applicationContext)
        binding.recyclerView.layoutManager = mLayoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.setHasFixedSize(false)

    }

    private fun initObservable() {
        viewModel.getIssueObservable().observe(this, Observer {
            binding.progressBar.visibility = View.GONE
            if (it.isNullOrEmpty()) {
                showDialogIfCommentNotExist()
                return@Observer
            }
            binding.recyclerView.adapter = IssueDetailsAdapter(it)
        })
    }

    private fun showDialogIfCommentNotExist() {
        WeakReference(this).get()?.run {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.comments_not_available)
            builder.setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            val dialog: AlertDialog = builder.create()
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.show()
        }
    }
    companion object {
        fun openCommentDetailsActivity(context: Context,issueTitle:String, issueId: Long, id: Int) {
            val intent = Intent(context, IssueDetailsActivity::class.java)
            intent.putExtra(ISSUE_TITLE_OBJECT, issueTitle)
            intent.putExtra(ISSUE_ID_OBJECT, issueId)
            intent.putExtra(ISSUE_COMMENT_ID_OBJECT, id)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            context.startActivity(intent)
        }
    }
}