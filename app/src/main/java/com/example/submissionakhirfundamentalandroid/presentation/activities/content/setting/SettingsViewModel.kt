package com.example.submissionakhirfundamentalandroid.presentation.activities.content.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.submissionakhirfundamentalandroid.utilities.datastore.SettingPrefs

import kotlinx.coroutines.launch

class SettingsViewModel(private val pref: SettingPrefs): ViewModel() {

    fun getTheme(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveTheme(isDarkMode: Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkMode)
        }
    }
}