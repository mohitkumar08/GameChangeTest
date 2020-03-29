package com.bit.gamechangetest.ui.issuelist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bit.gamechangetest.databinding.IssueItemViewLayoutBinding
import com.bit.gamechangetest.repository.server.IssueModel

class IssueAdapter(context: Context, private var itemsList: List<IssueModel>) :
    RecyclerView.Adapter<IssueAdapter.ViewHolder>() {
    private var onIssueInteractionListener: OnIssueInteractionListener =
        context as OnIssueInteractionListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(IssueItemViewLayoutBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = itemsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(itemsList[position])

    inner class ViewHolder(private val binding: IssueItemViewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(issueModel: IssueModel) {
            with(binding) {
                this.issueModelObject = issueModel
                this.rootView.setOnClickListener {
                    onIssueInteractionListener.onClickIssue(issueModel)
                }
            }
        }
    }

}

interface OnIssueInteractionListener {
    fun onClickIssue(issueModel: IssueModel)
}