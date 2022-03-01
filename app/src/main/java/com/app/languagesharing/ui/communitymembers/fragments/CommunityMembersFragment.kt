package com.app.languagesharing.ui.communitymembers.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.common.LIKED
import com.app.common.UN_LIKED
import com.app.common.models.CommunityMemberLike
import com.app.languagesharing.R
import com.app.languagesharing.base.BaseFragment
import com.app.languagesharing.databinding.FragmentCommunityMembersBinding
import com.app.languagesharing.ui.communitymembers.adapter.CommunityAdapter
import com.app.languagesharing.ui.communitymembers.viewmodel.CommunityMemberViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityMembersFragment : BaseFragment<FragmentCommunityMembersBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCommunityMembersBinding
        get() = FragmentCommunityMembersBinding::inflate

    private val mViewModel by viewModels<CommunityMemberViewModel>()

    private lateinit var mAdapter: CommunityAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        addLoadStateListener()
        bindViewModel()
    }

    private fun addLoadStateListener() {
        mAdapter.addLoadStateListener { loadState ->
            bi.progressBar.isVisible = loadState.source.refresh is LoadState.Loading && mAdapter.itemCount <= 0
        }
    }

    private fun onItemClick(item: CommunityMemberLike) {
        mViewModel.setLike(item.id, item.isLiked)
    }

    private fun setUpAdapter() {
        with(bi.recyclerViewMembers) {
            adapter =
                CommunityAdapter(this@CommunityMembersFragment::onItemClick).also { mAdapter = it }
            layoutManager = LinearLayoutManager(requireContext())
            val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            divider.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
            addItemDecoration(divider)
        }
    }

    private fun bindViewModel() {
        mViewModel.communityMembers.observe(viewLifecycleOwner) {
            mAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }
}