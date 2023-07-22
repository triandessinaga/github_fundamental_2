package com.example.submissionakhirfundamentalandroid.domain.usecase

import com.example.submissionakhirfundamentalandroid.data.remote.UserDetail
import com.example.submissionakhirfundamentalandroid.domain.repository.DetailRepository
import com.example.submissionakhirfundamentalandroid.utilities.stateHandler.Resource
import io.reactivex.Observable

class DetailUserUseCase(private val detailRepository: DetailRepository) {
    fun execute(username: String): Observable<Resource<UserDetail>>{
        return detailRepository.getDetailUser(username)
    }
}