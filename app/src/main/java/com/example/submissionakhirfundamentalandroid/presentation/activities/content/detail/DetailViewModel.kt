package com.example.submissionakhirfundamentalandroid.presentation.activities.content.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionakhirfundamentalandroid.data.remote.UserDetail
import com.example.submissionakhirfundamentalandroid.domain.usecase.DetailUserUseCase
import com.example.submissionakhirfundamentalandroid.utilities.stateHandler.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailViewModel(
    private val detailUserUseCase: DetailUserUseCase
) : ViewModel() {

    private val _detailResult = MutableLiveData<Resource<UserDetail>>()
    val detailResult: LiveData<Resource<UserDetail>>
        get() = _detailResult

    fun getDetailUser(username: String){
        detailUserUseCase.execute(username)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({response ->
                _detailResult.postValue(Resource.Success(response.data))
            },{error ->
                _detailResult.postValue(Resource.Error(error.message.toString()))
            }).isDisposed
    }

}