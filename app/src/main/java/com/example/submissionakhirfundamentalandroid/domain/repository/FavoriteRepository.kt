package com.example.submissionakhirfundamentalandroid.domain.repository


import com.example.submissionakhirfundamentalandroid.domain.data.Favorite
import com.example.submissionakhirfundamentalandroid.utilities.stateHandler.Resource
import io.reactivex.Observable
interface FavoriteRepository {

    fun saveFavorite(account: Favorite): Observable<Resource<String>>
    fun getFavorites(): Observable<Resource<ArrayList<Favorite>>>
}