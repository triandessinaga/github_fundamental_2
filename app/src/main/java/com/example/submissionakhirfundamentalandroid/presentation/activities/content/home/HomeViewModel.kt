package com.example.submissionakhirfundamentalandroid.presentation.activities.content.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submissionakhirfundamentalandroid.data.remote.UserResponse
import com.example.submissionakhirfundamentalandroid.domain.usecase.AllUserUseCase
import com.example.submissionakhirfundamentalandroid.domain.usecase.SearchUsernameUseCase
import com.example.submissionakhirfundamentalandroid.utilities.datastore.SettingPrefs
import com.example.submissionakhirfundamentalandroid.utilities.stateHandler.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val allUserUseCase: AllUserUseCase,
    private val searchUsernameUseCase: SearchUsernameUseCase,
    private val sharedPref: SettingPrefs
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _allDataResult = MutableLiveData<Resource<ArrayList<UserResponse>>>()
    val allDataResult: LiveData<Resource<ArrayList<UserResponse>>> = _allDataResult

    private val _searchResult = MutableLiveData<Resource<ArrayList<UserResponse>>>()
    val searchResult: LiveData<Resource<ArrayList<UserResponse>>> = _searchResult

    private val _saveFavorite = MutableLiveData<Resource<String>>()
    val saveFavorite: LiveData<Resource<String>> = _saveFavorite

    fun getAllData() {
        _allDataResult.postValue(Resource.Loading())
        compositeDisposable.add(
            allUserUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ response ->
                    _allDataResult.postValue(Resource.Success(response.data))
                },{error ->
                    _allDataResult.postValue(Resource.Error(error.toString()))
                })
        )
    }

    fun searchUsername(username: String) {

        compositeDisposable.add(
            searchUsernameUseCase.execute(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    _searchResult.postValue(Resource.Success(response.data))
                }, { error ->
                    _searchResult.postValue(Resource.Error(error.toString()))
                })
        )
    }

    fun getTheme(): LiveData<Boolean> {
        return sharedPref.getThemeSetting().asLiveData()
    }
}