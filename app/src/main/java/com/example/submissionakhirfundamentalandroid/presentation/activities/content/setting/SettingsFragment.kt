package com.example.submissionakhirfundamentalandroid.presentation.activities.content.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissionakhirfundamentalandroid.R
import com.example.submissionakhirfundamentalandroid.databinding.FragmentSettingsBinding
import com.example.submissionakhirfundamentalandroid.presentation.base.BaseFragment
import com.example.submissionakhirfundamentalandroid.utilities.datastore.SettingPrefs
import com.example.submissionakhirfundamentalandroid.utilities.datastore.dataStore
import com.google.android.material.bottomnavigation.BottomNavigationView


class SettingsFragment : BaseFragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var bottomNav: BottomNavigationView? = null
    private var viewModel: SettingsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNav = activity?.findViewById(R.id.bottom_nav)
        val pref = SettingPrefs.getInstance(requireActivity().application.dataStore)
        viewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(SettingsViewModel::class.java)
        switchTheme()
        initListeners()
    }

    private fun initListeners() {
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked: Boolean ->
            viewModel?.saveTheme(isChecked)
        }
    }

    private fun switchTheme() {
        viewModel?.getTheme()?.observe(viewLifecycleOwner){isDarkModeActive: Boolean ->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bottomNav?.visibility = View.GONE
    }
}

class SettingViewModelFactory(
    private val settingPrefs: SettingPrefs
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)){
            return SettingsViewModel(settingPrefs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}