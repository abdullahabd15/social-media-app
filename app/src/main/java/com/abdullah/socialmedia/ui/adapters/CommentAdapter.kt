package com.abdullah.socialmedia.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdullah.socialmedia.common.extension.loadNetworkImage
import com.abdullah.socialmedia.databinding.ItemCommentBinding
import com.abdullah.socialmedia.model.Comment

class CommentAdapter: RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private val comments = mutableListOf<Comment>()

    fun setComments(comments: List<Comment>?) {
        comments?.let {
            this.comments.clear()
            this.comments.addAll(it)
            notifyItemRangeChanged(0, it.size)
        }
    }

    inner class CommentViewHolder(
        private val binding: ItemCommentBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun onBind(comment: Comment?) {
            binding.apply {
                imgUser.loadNetworkImage(comment?.authorImageUrl)
                tvAuthor.text = comment?.authorName
                tvBody.text = comment?.body
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.onBind(comments.getOrNull(position))
    }

    override fun getItemCount(): Int = comments.size
}