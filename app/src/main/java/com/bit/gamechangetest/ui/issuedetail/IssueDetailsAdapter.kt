package com.bit.gamechangetest.ui.issuedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bit.gamechangetest.databinding.IssueDetailItemLayoutBinding
import com.bit.gamechangetest.repository.server.CommentModel
import com.github.vipulasri.timelineview.TimelineView
import com.squareup.picasso.Picasso


class IssueDetailsAdapter(private var itemsList: List<CommentModel>) :
    RecyclerView.Adapter<IssueDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = IssueDetailItemLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    override fun getItemCount(): Int = itemsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(itemsList[position])

    inner class ViewHolder(
        private val binding: IssueDetailItemLayoutBinding,
        private val viewType: Int
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(commentModel: CommentModel) {
            binding.timeline.initLine(viewType)
            binding.commentModelObject = commentModel
            binding.ivUser.setImageResource(0)
            if (commentModel.user.avatarUrl.isNullOrEmpty().not()) {
                Picasso.get().load(commentModel.user.avatarUrl).into(binding.ivUser)
            }
        }
    }

}