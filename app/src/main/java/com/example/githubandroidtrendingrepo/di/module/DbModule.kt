package com.example.githubandroidtrendingrepo.di.module

import android.app.Application
import androidx.room.Room
import com.example.githubandroidtrendingrepo.data.AppDatabase
import com.example.githubandroidtrendingrepo.data.dao.GithubDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *Created by Priyanka K on 9/22/2021
 */
@Module
class DbModule {
    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application,
                AppDatabase::class.java, "Github.db")
                .allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    internal fun provideGithubDao(appDatabase: AppDatabase): GithubDao {
        return appDatabase.githubDao()
    }
}