package com.abdullah.socialmedia.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdullah.socialmedia.R
import com.abdullah.socialmedia.common.extension.loadNetworkImage
import com.abdullah.socialmedia.common.interfaces.IItemListClickListener
import com.abdullah.socialmedia.databinding.ItemPostBinding
import com.abdullah.socialmedia.model.Post

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val posts = mutableListOf<Post>()
    private var listener: IItemListClickListener<Post?>? = null
    private var actionListener: IPostActionListener? = null

    fun setPosts(data: List<Post>?) {
        data?.let {
            posts.clear()
            posts.addAll(it)
            notifyItemRangeChanged(0, it.size)
        }
    }

    fun setItemClickListener(listener: IItemListClickListener<Post?>) {
        this.listener = listener
    }

    fun setActionClickListener(actionListener: IPostActionListener) {
        this.actionListener = actionListener
    }

    inner class PostViewHolder(
        private val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(post: Post?) {
            binding.apply {
                layoutPost.apply {
                    imgUser.loadNetworkImage(post?.userImageUrl)
                    post?.imageUrl?.let { imgPost.loadNetworkImage(it) }
                    tvAuthor.text = post?.userName
                    tvBody.text = post?.body
                    imgUser.setOnClickListener { actionListener?.onPostOwnerClicked(post) }
                    tvAuthor.setOnClickListener { actionListener?.onPostOwnerClicked(post) }
                    imgShare.setOnClickListener { actionListener?.onSharePostClicked(post) }
                }
                post?.comments?.size?.let {
                    tvCommentCount.text = root.context.getString(R.string.count_of_comments, it.toString())
                }
                root.setOnClickListener { listener?.onItemClickListener(post) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.onBind(posts.getOrNull(position))
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    interface IPostActionListener {
        fun onPostOwnerClicked(post: Post?)
        fun onSharePostClicked(post: Post?)
    }
}