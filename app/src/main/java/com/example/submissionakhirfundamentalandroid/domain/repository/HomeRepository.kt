package com.example.submissionakhirfundamentalandroid.domain.repository

import com.example.submissionakhirfundamentalandroid.data.remote.UserResponse
import com.example.submissionakhirfundamentalandroid.utilities.stateHandler.Resource
import io.reactivex.Observable


interface HomeRepository {

    fun getAllData(): Observable<Resource<ArrayList<UserResponse>>>
    fun searchByUsername(username: String): Observable<Resource<ArrayList<UserResponse>>>
}