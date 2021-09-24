package com.example.githubandroidtrendingrepo.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubandroidtrendingrepo.di.ViewModelKey
import com.example.githubandroidtrendingrepo.ui.factory.ViewModelFactory
import com.example.githubandroidtrendingrepo.ui.viewmodel.RepoListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 *Created by Priyanka K on 9/22/2021
 */
@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RepoListViewModel::class)
    protected abstract fun githubListViewModel(githubListViewModel: RepoListViewModel): ViewModel
}