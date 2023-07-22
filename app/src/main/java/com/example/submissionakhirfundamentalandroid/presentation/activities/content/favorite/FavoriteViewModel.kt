package com.example.submissionakhirfundamentalandroid.presentation.activities.content.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionakhirfundamentalandroid.domain.data.Favorite
import com.example.submissionakhirfundamentalandroid.domain.usecase.GetFavoritesLocalUseCase
import com.example.submissionakhirfundamentalandroid.utilities.stateHandler.Resource


import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FavoriteViewModel(private val getFavorites: GetFavoritesLocalUseCase) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _favorites = MutableLiveData<Resource<ArrayList<Favorite>>>()
    val favorites: LiveData<Resource<ArrayList<Favorite>>> = _favorites


    fun getFavorites(){
        compositeDisposable.add(
            getFavorites.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({value ->
                    _favorites.postValue(value)
                },{error ->
                    _favorites.postValue(Resource.Error(error.message.toString()))
                })
        )
    }
}