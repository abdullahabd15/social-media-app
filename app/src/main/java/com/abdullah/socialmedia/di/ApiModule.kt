package com.abdullah.socialmedia.di

import com.abdullah.socialmedia.api.SocialMediaApi
import com.abdullah.socialmedia.repository.SocialMediaRepository
import com.abdullah.socialmedia.repository.SocialMediaRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideSocialMediaApi(
        retrofit: Retrofit
    ): SocialMediaApi = retrofit.create(SocialMediaApi::class.java)

    @Provides
    @Singleton
    fun provideSocialMediaRepository(
        api: SocialMediaApi
    ): SocialMediaRepository = SocialMediaRepositoryImpl(api)
}