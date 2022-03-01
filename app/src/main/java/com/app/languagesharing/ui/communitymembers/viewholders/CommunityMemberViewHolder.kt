package com.app.languagesharing.ui.communitymembers.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.app.common.models.CommunityMemberLike
import com.app.languagesharing.R
import com.app.languagesharing.databinding.LayoutCommunityMemberItemBinding
import com.app.languagesharing.typealiases.ItemClickListener
import com.app.languagesharing.utils.makeGone
import com.app.languagesharing.utils.makeVisible

class CommunityMemberViewHolder(
    private val binding: LayoutCommunityMemberItemBinding,
    private val listener: ItemClickListener<CommunityMemberLike>
) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup, listener: ItemClickListener<CommunityMemberLike>) = CommunityMemberViewHolder(
            LayoutCommunityMemberItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )
    }

    fun onBind(item: CommunityMemberLike) {
        with(binding) {
            name.text = item.firstName
            topic.text = item.topic
            tvNativeValues.text = item.natives()
            tvLearnValues.text = item.learns()
            picture.load(item.pictureUrl) {
                crossfade(true)
                transformations(RoundedCornersTransformation(30f))
            }

            if (item.isNewMember) {
                tvNew.makeVisible()
                referenceCount.makeGone()
            }
            else {
                with(referenceCount) {
                    makeVisible()
                    text = item.referenceCnt.toString()
                }
                tvNew.makeGone()
            }

            if (item.hasLiked()) {
                likeImg.setImageDrawable(
                    ContextCompat.getDrawable(
                        likeImg.context,
                        R.drawable.liked
                    )
                )
            }
            else {
                likeImg.setImageDrawable(
                    ContextCompat.getDrawable(
                        likeImg.context,
                        R.drawable.like
                    )
                )
            }

            likeImg.setOnClickListener {
                listener.invoke(item)
            }
        }
    }
}