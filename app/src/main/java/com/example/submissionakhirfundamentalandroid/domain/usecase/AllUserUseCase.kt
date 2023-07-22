package com.example.submissionakhirfundamentalandroid.domain.usecase

import com.example.submissionakhirfundamentalandroid.data.remote.UserResponse
import com.example.submissionakhirfundamentalandroid.domain.repository.HomeRepository
import com.example.submissionakhirfundamentalandroid.utilities.stateHandler.Resource
import io.reactivex.Observable

class AllUserUseCase(private val homeRepository: HomeRepository) {
    fun execute(): Observable<Resource<ArrayList<UserResponse>>>{
        return homeRepository.getAllData()
    }
}