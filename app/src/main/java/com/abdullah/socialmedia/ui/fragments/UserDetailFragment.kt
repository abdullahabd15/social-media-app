package com.abdullah.socialmedia.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.abdullah.socialmedia.R
import com.abdullah.socialmedia.common.Const
import com.abdullah.socialmedia.common.Resource
import com.abdullah.socialmedia.common.extension.gone
import com.abdullah.socialmedia.common.extension.loadNetworkImage
import com.abdullah.socialmedia.common.extension.showToast
import com.abdullah.socialmedia.common.extension.visible
import com.abdullah.socialmedia.common.interfaces.IItemListClickListener
import com.abdullah.socialmedia.databinding.FragmentUserDetailBinding
import com.abdullah.socialmedia.model.Album
import com.abdullah.socialmedia.model.User
import com.abdullah.socialmedia.ui.adapters.AlbumAdapter
import com.abdullah.socialmedia.ui.viewmodels.UserDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class UserDetailFragment : Fragment(), IItemListClickListener<Album?> {
    private var binding: FragmentUserDetailBinding? = null
    private var userName: String = ""
    private val args: UserDetailFragmentArgs by navArgs()
    private val userId: Int get() = args.userId
    private val viewModel: UserDetailViewModel by viewModels()

    @Inject
    lateinit var adapter: AlbumAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.layoutToolbar?.toolbar?.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        initRecyclerView()
        observeUser()
    }

    override fun onItemClickListener(data: Album?) {
        data?.let {
            navigateToPhotoFragment(it.imageUrl)
        }
    }

    private fun initRecyclerView() {
        val gridLayoutManager =
            GridLayoutManager(context, Const.DEFAULT_SPAN_COUNT, GridLayoutManager.VERTICAL, false)
        adapter.setOnPhotoClickListener(this)
        binding?.rvAlbums?.apply {
            this.adapter = this@UserDetailFragment.adapter
            this.layoutManager = gridLayoutManager
        }
    }

    private fun observeUser() {
        lifecycleScope.launchWhenResumed {
            viewModel.user.collect {
                when (it) {
                    is Resource.Init -> viewModel.getUserById(userId)
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> {
                        hideLoading()
                        if (it.data != null) {
                            userName = it.data.userName
                            setDataToView(it.data)
                        } else {
                            showToast(getString(R.string.user_not_found))
                        }
                    }
                    is Resource.Error -> {
                        hideLoading()
                        showToast(it.message ?: getString(R.string.default_error_message))
                    }
                }
            }
        }
    }

    private fun setDataToView(user: User) {
        binding?.apply {
            user.albums.getOrNull(0)?.imageUrl?.let { imgUrl ->
                imgUser.loadNetworkImage(imgUrl)
            }
            tvUserName.text = user.userName
            tvEmail.text = user.email
            tvAddress.text = user.address
            tvCompany.text = user.company
        }
        adapter.setAlbums(user.albums)
    }

    private fun showLoading() {
        binding?.apply {
            imgAnimatedLoading.visible()
            scrollView.gone()
        }
    }

    private fun hideLoading() {
        binding?.apply {
            imgAnimatedLoading.gone()
            scrollView.visible()
        }
    }

    private fun navigateToPhotoFragment(imageUrl: String) {
        val direction =
            UserDetailFragmentDirections.actionUserDetailFragmentToPhotoFragment(imageUrl, userName)
        findNavController().navigate(direction)
    }
}