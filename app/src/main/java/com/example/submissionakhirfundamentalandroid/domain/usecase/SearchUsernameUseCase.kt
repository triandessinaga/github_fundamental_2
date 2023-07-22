package com.example.submissionakhirfundamentalandroid.domain.usecase

import com.example.submissionakhirfundamentalandroid.data.remote.UserResponse
import com.example.submissionakhirfundamentalandroid.domain.repository.HomeRepository
import com.example.submissionakhirfundamentalandroid.utilities.stateHandler.Resource
import io.reactivex.Observable

class SearchUsernameUseCase(private val homeRepository: HomeRepository) {
    fun execute(username: String): Observable<Resource<ArrayList<UserResponse>>>{
        return homeRepository.searchByUsername(username)
    }
}