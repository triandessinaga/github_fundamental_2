package com.example.submissionakhirfundamentalandroid.data.remote.service

import com.example.submissionakhirfundamentalandroid.data.remote.SearchResponse
import com.example.submissionakhirfundamentalandroid.data.remote.UserDetail
import com.example.submissionakhirfundamentalandroid.data.remote.UserResponse
import com.example.submissionakhirfundamentalandroid.utilities.libraries.Routes
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GithubService {

    @GET(Routes.SEARCH)
    fun getSearchUsers(@Query("q")query: String): Observable<SearchResponse>

    @GET(Routes.ALL_DATA)
    fun getAllUsers(): Observable<ArrayList<UserResponse>>

    @GET(Routes.DETAIL_DATA+"{username}")
    fun getDetailUsers(@Path("username")username: String): Observable<UserDetail>

    @GET(Routes.DETAIL_DATA+"{username}"+ Routes.FOLLOWER)
    fun getFollowers(@Path("username")username: String): Observable<ArrayList<UserResponse>>

    @GET(Routes.DETAIL_DATA+"{username}"+ Routes.FOLLOWING)
    fun getFollowing(@Path("username")username: String): Observable<ArrayList<UserResponse>>
}