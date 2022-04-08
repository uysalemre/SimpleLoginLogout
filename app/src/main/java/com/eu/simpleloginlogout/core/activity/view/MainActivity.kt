package com.eu.simpleloginlogout.core.activity.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eu.simpleloginlogout.R
import com.eu.simpleloginlogout.core.activity.viewmodel.MainActivityViewModel
import com.eu.simpleloginlogout.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Emre UYSAL
 * MainActivity container and start point for this app
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val activityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initializeView()
    }

    private fun initializeView() = with(binding) {
        lifecycleOwner = this@MainActivity
        viewModel = activityViewModel
    }
}