package com.abdullah.socialmedia.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdullah.socialmedia.common.extension.gone
import com.abdullah.socialmedia.common.extension.loadNetworkImage
import com.abdullah.socialmedia.common.extension.visible
import com.abdullah.socialmedia.common.interfaces.IItemListClickListener
import com.abdullah.socialmedia.databinding.ItemAlbumBinding
import com.abdullah.socialmedia.model.Album

class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    private val albums = mutableListOf<Album>()
    private var listener: IItemListClickListener<Album?>? = null

    fun setOnPhotoClickListener(listener: IItemListClickListener<Album?>) {
        this.listener = listener
    }

    fun setAlbums(albums: List<Album>) {
        this.albums.clear()
        this.albums.addAll(albums)
        notifyItemRangeChanged(0, albums.size)
    }

    inner class AlbumViewHolder(
        private val binding: ItemAlbumBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(album: Album?) {
            binding.apply {
                imgAlbum.loadNetworkImage(album?.imageUrl) { isLoading ->
                    if (isLoading) progressImageLoading.visible() else progressImageLoading.gone()
                }
                imgAlbum.setOnClickListener { listener?.onItemClickListener(album) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.onBind(albums.getOrNull(position))
    }

    override fun getItemCount(): Int = albums.size
}