package com.abdullah.socialmedia.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullah.socialmedia.R
import com.abdullah.socialmedia.common.Resource
import com.abdullah.socialmedia.common.extension.gone
import com.abdullah.socialmedia.common.extension.showToast
import com.abdullah.socialmedia.common.extension.visible
import com.abdullah.socialmedia.common.interfaces.IItemListClickListener
import com.abdullah.socialmedia.databinding.FragmentPostsBinding
import com.abdullah.socialmedia.model.Post
import com.abdullah.socialmedia.ui.adapters.PostAdapter
import com.abdullah.socialmedia.ui.viewmodels.PostsViewModel
import com.abdullah.socialmedia.utils.ShareUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class PostsFragment : Fragment(), IItemListClickListener<Post?>, PostAdapter.IPostActionListener {
    private var binding: FragmentPostsBinding? = null
    private val viewModel: PostsViewModel by navGraphViewModels(R.id.main_navigation) {
        defaultViewModelProviderFactory
    }

    @Inject
    lateinit var adapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observePosts()
    }

    override fun onItemClickListener(data: Post?) {
        data?.let {
            navigateToDetailPost(it)
        }
    }

    override fun onPostOwnerClicked(post: Post?) {
        post?.let {
            navigateToUser(it.userId)
        }
    }

    override fun onSharePostClicked(post: Post?) {
        context?.let { ShareUtil.sharePost(it, "${post?.userName}\n\n${post?.body}") }
    }

    private fun navigateToDetailPost(post: Post) {
        val destination = PostsFragmentDirections.actionPostsFragmentToPostDetailFragment(post)
        findNavController().navigate(destination)
    }

    private fun navigateToUser(userId: Int) {
        val destination = PostsFragmentDirections.actionPostsFragmentToUserDetailFragment(userId)
        findNavController().navigate(destination)
    }

    private fun observePosts() {
        lifecycleScope.launchWhenResumed {
            viewModel.posts.collect {
                when (it) {
                    is Resource.Init -> viewModel.findAllPosts()
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> {
                        hideLoading()
                        adapter.setPosts(it.data)
                    }
                    is Resource.Error -> {
                        hideLoading()
                        showToast(it.message ?: getString(R.string.default_error_message))
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding?.apply {
            imgAnimatedLoading.visible()
            rvPosts.gone()
        }
    }

    private fun hideLoading() {
        binding?.apply {
            imgAnimatedLoading.gone()
            rvPosts.visible()
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter.setActionClickListener(this)
        adapter.setItemClickListener(this)
        binding?.rvPosts?.apply {
            this.adapter = this@PostsFragment.adapter
            this.layoutManager = layoutManager
        }
    }
}