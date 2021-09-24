package com.example.githubandroidtrendingrepo.data.services

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *Created by Priyanka K on 9/21/2021
 */
interface GithubApiService {

    @GET("search/repositories")
    fun fetchRepositories(@Query("sort") sort: String,
                          @Query("order") order: String,
                          @Query("page") page: Long): Observable<Response<GithubApiResponse>>
}