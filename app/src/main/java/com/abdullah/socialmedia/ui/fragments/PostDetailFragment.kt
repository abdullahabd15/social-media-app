package com.abdullah.socialmedia.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullah.socialmedia.common.extension.loadNetworkImage
import com.abdullah.socialmedia.databinding.FragmentPostDetailBinding
import com.abdullah.socialmedia.ui.adapters.CommentAdapter
import com.abdullah.socialmedia.utils.ShareUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostDetailFragment : Fragment() {
    private var binding: FragmentPostDetailBinding? = null
    private val args: PostDetailFragmentArgs by navArgs()
    private val post get() = args.post

    @Inject
    lateinit var adapter: CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        initRecyclerView()
        setDataToView()

        binding?.post?.apply {
            imgUser.setOnClickListener { navigateToUserDetail() }
            tvAuthor.setOnClickListener { navigateToUserDetail() }
            imgShare.setOnClickListener { sharePost() }
        }
    }

    private fun sharePost() {
        context?.let { ShareUtil.sharePost(it, "${post.userName}\n\n${post.body}") }
    }

    private fun navigateToUserDetail() {
        val destination =
            PostDetailFragmentDirections.actionPostDetailFragmentToUserDetailFragment(post.userId)
        findNavController().navigate(destination)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding?.rvComments?.apply {
            this.adapter = this@PostDetailFragment.adapter
            this.layoutManager = layoutManager
        }
    }

    private fun setDataToView() {
        binding?.post?.apply {
            imgUser.loadNetworkImage(post.userImageUrl)
            tvAuthor.text = post.userName
            tvBody.text = post.body
            post.imageUrl?.let {
                imgPost.loadNetworkImage(it)
            }
        }
        adapter.setComments(post.comments)
    }

    private fun setupToolbar() {
        binding?.layoutToolbar?.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}