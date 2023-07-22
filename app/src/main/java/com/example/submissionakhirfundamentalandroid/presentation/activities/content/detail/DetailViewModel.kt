package com.example.submissionakhirfundamentalandroid.presentation.activities.content.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionakhirfundamentalandroid.data.local.realm.FavoriteRealm
import com.example.submissionakhirfundamentalandroid.data.remote.UserDetail
import com.example.submissionakhirfundamentalandroid.domain.usecase.DeleteFavoriteUseCase
import com.example.submissionakhirfundamentalandroid.domain.usecase.DetailUserUseCase
import com.example.submissionakhirfundamentalandroid.domain.usecase.GetFavoriteLocalUseCase
import com.example.submissionakhirfundamentalandroid.domain.usecase.SaveFavoriteUseCase
import com.example.submissionakhirfundamentalandroid.utilities.stateHandler.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailViewModel(
    private val detailUserUseCase: DetailUserUseCase, private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val getFavoriteLocalUseCase: GetFavoriteLocalUseCase, private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _detailResult = MutableLiveData<Resource<UserDetail>>()
    val detailResult: LiveData<Resource<UserDetail>> = _detailResult

    private val _saveResult = MutableLiveData<Resource<FavoriteRealm>>()
    val saveResult: LiveData<Resource<FavoriteRealm>> = _saveResult

    private val _saveFavorite = MutableLiveData<Resource<String>>()
    val saveFavorite: LiveData<Resource<String>> = _saveFavorite

    private val _isSaved = MutableLiveData<Resource<Boolean>>()
    val isSaved: LiveData<Resource<Boolean>> = _isSaved

    private val _isDeleted = MutableLiveData<Resource<String>>()
    val isDeleted: LiveData<Resource<String>> = _isDeleted

    fun getDetailUser(username: String) {
        detailUserUseCase.execute(username)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                _detailResult.postValue(Resource.Success(response.data))
            }, { error ->
                _detailResult.postValue(Resource.Error(error.message.toString()))
            }).isDisposed
    }

    fun saveFavorite(username: String) {
        compositeDisposable.add(
            saveFavoriteUseCase.execute(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ value ->
                    _saveFavorite.postValue(value)
                }, { error ->
                    _saveFavorite.postValue(Resource.Error(error.message.toString()))
                })
        )
    }

    fun getFavoriteLocal(username: String) {
        compositeDisposable.add(
            getFavoriteLocalUseCase.execute(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ value ->
                    _isSaved.postValue(value)
                }, { error ->
                    _isSaved.postValue(Resource.Error(error.toString()))
                })
        )
    }

    fun deleteFavorite(username: String) {
        compositeDisposable.add(
            deleteFavoriteUseCase.execute(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    _isDeleted.postValue(result)
                }, { error ->
                    _isDeleted.postValue(Resource.Error(error.message.toString()))
                })
        )
    }


}