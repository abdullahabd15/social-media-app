package com.abdullah.socialmedia.di

import com.abdullah.socialmedia.ui.adapters.AlbumAdapter
import com.abdullah.socialmedia.ui.adapters.CommentAdapter
import com.abdullah.socialmedia.ui.adapters.PostAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    @FragmentScoped
    fun providePostAdapter(): PostAdapter = PostAdapter()

    @Provides
    @FragmentScoped
    fun provideCommentAdapter(): CommentAdapter = CommentAdapter()

    @Provides
    @FragmentScoped
    fun provideAlbumAdapter(): AlbumAdapter = AlbumAdapter()
}