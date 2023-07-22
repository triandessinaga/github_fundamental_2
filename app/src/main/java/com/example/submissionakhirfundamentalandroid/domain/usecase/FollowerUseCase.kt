package com.example.submissionakhirfundamentalandroid.domain.usecase

import com.example.submissionakhirfundamentalandroid.data.remote.UserResponse
import com.example.submissionakhirfundamentalandroid.domain.repository.DetailRepository
import com.example.submissionakhirfundamentalandroid.utilities.stateHandler.Resource
import io.reactivex.Observable

class FollowerUseCase(private val detailRepository: DetailRepository) {
    fun execute(username: String): Observable<Resource<ArrayList<UserResponse>>>{
        return detailRepository.getFollowers(username)
    }
}