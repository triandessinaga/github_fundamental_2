package com.example.submissionakhirfundamentalandroid.domain.repository

import com.example.submissionakhirfundamentalandroid.data.remote.UserDetail
import com.example.submissionakhirfundamentalandroid.data.remote.UserResponse
import com.example.submissionakhirfundamentalandroid.utilities.stateHandler.Resource
import io.reactivex.Observable

interface DetailRepository {

    fun getDetailUser(username: String): Observable<Resource<UserDetail>>
    fun getFollowers(username: String): Observable<Resource<ArrayList<UserResponse>>>
    fun getFollowing(username: String): Observable<Resource<ArrayList<UserResponse>>>
}