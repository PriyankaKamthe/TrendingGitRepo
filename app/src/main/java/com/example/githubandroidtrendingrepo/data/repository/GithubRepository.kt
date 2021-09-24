package com.example.githubandroidtrendingrepo.data.repository

import com.example.githubandroidtrendingrepo.data.NetworkBoundResource
import com.example.githubandroidtrendingrepo.data.Resource
import com.example.githubandroidtrendingrepo.data.dao.GithubDao
import com.example.githubandroidtrendingrepo.data.entity.GitRepoEntity
import com.example.githubandroidtrendingrepo.data.services.GithubApiResponse
import com.example.githubandroidtrendingrepo.data.services.GithubApiService
import com.example.githubandroidtrendingrepo.utils.AppConstants.Companion.QUERY_ORDER
import com.example.githubandroidtrendingrepo.utils.AppConstants.Companion.QUERY_SORT
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Singleton

/**
 *Created by Priyanka K on 9/21/2021
 */
@Singleton
class GithubRepository(private val githubDao: GithubDao, private val githubApiService: GithubApiService) {
    fun getRepositories(page: Long): Observable<Resource<List<GitRepoEntity>>> {
        return object : NetworkBoundResource<List<GitRepoEntity>, GithubApiResponse>() {

            override fun saveCallResult(item: GithubApiResponse) {
                val repositories = item.items
                for (githubEntity in repositories) {
                    githubEntity.page = page
                    githubEntity.totalPages = item.totalCount
                }
                githubDao.insertRepositories(repositories)
            }

            override fun shouldFetch(): Boolean {
                return true
            }

            override fun loadFromDb(): Flowable<List<GitRepoEntity>> {
                val repositories = githubDao.getRepositoriesByPage(page)
                return Flowable.just(repositories)
            }

            override fun createCall(): Observable<Resource<GithubApiResponse>> {
                return githubApiService.fetchRepositories(QUERY_SORT, QUERY_ORDER, page)
                    .flatMap<Resource<GithubApiResponse>> { response ->
                        Observable.just(if (response.isSuccessful)
                            Resource.success(response.body()!!)
                        else
                            Resource.error("", GithubApiResponse(0, emptyList())))
                    }
            }

        }.getAsObservable()
    }
}