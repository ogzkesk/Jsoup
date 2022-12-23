package com.example.jsoupapp.di

import com.example.jsoupapp.data.remote.Api
import com.example.jsoupapp.data.remote.DefaultApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi() : Api =
        DefaultApiImpl()

}