package com.example.githubandroidtrendingrepo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubandroidtrendingrepo.data.entity.GitRepoEntity

/**
 *Created by Priyanka K on 9/21/2021
 */
@Dao
interface GithubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositories(githubEntities: List<GitRepoEntity>): LongArray

    @Query("SELECT * FROM `GitRepoEntity` where page = :page")
    fun getRepositoriesByPage(page: Long): List<GitRepoEntity>
}