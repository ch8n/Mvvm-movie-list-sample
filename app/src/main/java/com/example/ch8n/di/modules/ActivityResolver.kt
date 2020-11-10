package com.example.ch8n.di.modules

import com.example.ch8n.detail.MovieDetailActivity
import com.example.ch8n.detail.di.MovieDetailModule
import com.example.ch8n.search.MovieSearchActivity
import com.example.ch8n.search.di.MovieSearchModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityResolver {

    @ContributesAndroidInjector(modules = arrayOf(MovieSearchModule::class))
    abstract fun provideSearchActivity(): MovieSearchActivity

    @ContributesAndroidInjector(modules = arrayOf(MovieDetailModule::class))
    abstract fun provideDetailActivity(): MovieDetailActivity
}