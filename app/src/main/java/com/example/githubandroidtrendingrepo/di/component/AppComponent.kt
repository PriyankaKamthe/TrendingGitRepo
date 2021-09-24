package com.example.githubandroidtrendingrepo.di.component

import android.app.Application
import com.example.githubandroidtrendingrepo.di.module.ActivityModule
import com.example.githubandroidtrendingrepo.di.module.ApiModule
import com.example.githubandroidtrendingrepo.di.module.DbModule
import com.example.githubandroidtrendingrepo.di.module.ViewModelModule
import com.example.githubandroidtrendingrepo.utils.AppController
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 *Created by Priyanka K on 9/22/2021
 */
@Component(modules = [ApiModule::class,
    DbModule::class,
    ViewModelModule::class,
    ActivityModule::class,
    AndroidSupportInjectionModule::class])
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }


    fun inject(appController: AppController)
}