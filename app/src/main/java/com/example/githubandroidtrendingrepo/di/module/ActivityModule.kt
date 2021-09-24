package com.example.githubandroidtrendingrepo.di.module

import com.example.githubandroidtrendingrepo.ui.activity.RepoListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *Created by Priyanka K on 9/22/2021
 */
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributeGithubListActivity(): RepoListActivity
}