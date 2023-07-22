package com.example.submissionakhirfundamentalandroid.presentation.activities

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.submissionakhirfundamentalandroid.R
import com.example.submissionakhirfundamentalandroid.databinding.ActivityMainBinding

import com.example.submissionakhirfundamentalandroid.presentation.base.BaseActivity
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : BaseActivity () {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private var switchTheme: SwitchMaterial? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupBottomView()
    }

    private fun setupBottomView(){
        navHostFragment= supportFragmentManager.findFragmentById(R.id.mainHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        binding?.bottomNav?.setupWithNavController(navController)
    }
}
