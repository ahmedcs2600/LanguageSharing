package com.app.languagesharing.ui.communitymembers.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.app.common.models.CommunityMemberLike
import com.app.languagesharing.typealiases.ItemClickListener
import com.app.languagesharing.ui.communitymembers.viewholders.CommunityMemberViewHolder

class CommunityAdapter(private val listener: ItemClickListener<CommunityMemberLike>) :
    PagingDataAdapter<CommunityMemberLike, CommunityMemberViewHolder>(COMPARATOR) {

    override fun onBindViewHolder(holder: CommunityMemberViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.onBind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityMemberViewHolder {
        return CommunityMemberViewHolder.create(parent,listener)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<CommunityMemberLike>() {
            override fun areItemsTheSame(
                oldItem: CommunityMemberLike,
                newItem: CommunityMemberLike
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CommunityMemberLike, newItem: CommunityMemberLike) =
                oldItem == newItem
        }
    }
}