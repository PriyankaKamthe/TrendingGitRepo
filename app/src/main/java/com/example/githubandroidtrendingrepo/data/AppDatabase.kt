package com.example.githubandroidtrendingrepo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.githubandroidtrendingrepo.data.dao.GithubDao
import com.example.githubandroidtrendingrepo.data.entity.GitRepoEntity
import com.example.githubandroidtrendingrepo.utils.TimestampConverter

/**
 *Created by Priyanka K on 9/22/2021
 */
@Database(entities = [GitRepoEntity::class], version = 1, exportSchema = false)
@TypeConverters(TimestampConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun githubDao(): GithubDao
}
