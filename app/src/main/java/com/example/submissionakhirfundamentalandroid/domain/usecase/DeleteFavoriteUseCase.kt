package com.example.submissionakhirfundamentalandroid.domain.usecase
import com.example.submissionakhirfundamentalandroid.domain.repository.DetailRepository
import com.example.submissionakhirfundamentalandroid.utilities.stateHandler.Resource
import io.reactivex.Observable

class DeleteFavoriteUseCase(private val repository: DetailRepository) {
    fun execute(username: String): Observable<Resource<String>>{
        return repository.deleteFavorite(username)
    }
}