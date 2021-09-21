package com.abdullah.socialmedia.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.abdullah.socialmedia.common.extension.loadNetworkImage
import com.abdullah.socialmedia.databinding.FragmentPhotoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoFragment : Fragment() {
    private var binding: FragmentPhotoBinding? = null
    private val args: PhotoFragmentArgs by navArgs()
    private val userName: String get() = args.userName
    private val imageUrl: String? get() = args.imageUrl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        binding?.imgPhoto?.loadNetworkImage(imageUrl)
    }

    private fun setupToolbar() {
        binding?.toolbar?.apply {
            title = userName
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}