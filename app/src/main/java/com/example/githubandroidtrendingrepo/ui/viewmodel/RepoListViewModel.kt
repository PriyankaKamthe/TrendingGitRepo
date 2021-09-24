package com.example.githubandroidtrendingrepo.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.githubandroidtrendingrepo.data.SingleLiveEvent
import com.example.githubandroidtrendingrepo.data.dao.GithubDao
import com.example.githubandroidtrendingrepo.data.entity.GitRepoEntity
import com.example.githubandroidtrendingrepo.data.repository.GithubRepository
import com.example.githubandroidtrendingrepo.data.services.GithubApiService
import java.util.ArrayList
import javax.inject.Inject

/**
 *Created by Priyanka K on 9/20/2021
 */
class RepoListViewModel @Inject
constructor(githubDao: GithubDao, githubApiService: GithubApiService):ViewModel(){
    private var currentPage: Long = 0L
    private val repositories = ArrayList<GitRepoEntity>()
    private val repositoryListLiveData = SingleLiveEvent<List<GitRepoEntity>>()
    private val repository: GithubRepository = GithubRepository(githubDao, githubApiService)

    fun fetchRepositories() {
        repository.getRepositories((++currentPage))
            .subscribe { resource ->
                if (resource.isLoaded) {
                    resource.data?.let { repositories.addAll(it) }
                    repositoryListLiveData.postValue(resource.data)
                }
            }
    }

    fun isLastPage(): Boolean {
        return repositoryListLiveData.value?.let { it[0].isLastPage() }
            ?: run {
                false
            }
    }

    fun getRepositoryLiveData() = repositoryListLiveData

    fun getRepositories(): List<GitRepoEntity> {
        return repositories
    }

}