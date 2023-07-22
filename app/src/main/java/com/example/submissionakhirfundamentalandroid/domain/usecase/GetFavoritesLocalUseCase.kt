package com.example.submissionakhirfundamentalandroid.domain.usecase

import com.example.submissionakhirfundamentalandroid.domain.data.Favorite
import com.example.submissionakhirfundamentalandroid.domain.repository.FavoriteRepository
import com.example.submissionakhirfundamentalandroid.utilities.stateHandler.Resource
import io.reactivex.Observable

class GetFavoritesLocalUseCase(private val repository: FavoriteRepository) {
    fun execute(): Observable<Resource<ArrayList<Favorite>>>{
        return repository.getFavorites()
    }
}