package com.eu.simpleloginlogout.profile

import android.annotation.SuppressLint
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eu.simpleloginlogout.R
import com.eu.simpleloginlogout.base.BaseFragment
import com.eu.simpleloginlogout.core.ui.toast
import com.eu.simpleloginlogout.databinding.FragmentProfileBinding
import com.eu.simpleloginlogout.profile.viewmodel.ProfileViewModel
import com.eu.simpleloginlogout.profile.viewmodel.intent.ProfileIntent
import com.eu.simpleloginlogout.profile.viewmodel.state.ProfileState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * @author Emre UYSAL
 * Profile fragment that contains user information and logout functionality
 */
@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val viewModel: ProfileViewModel by viewModels()

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreationCompleted() {
        setHasOptionsMenu(true)
        binding.profileViewModel = viewModel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is ProfileState.Error -> {
                            hideLoading()
                            context?.toast(
                                getString(it.message).plus("\n")
                                    .plus(getString(R.string.swipe_refresh))
                            )
                        }
                        ProfileState.Init -> {}
                        ProfileState.Loading -> showLoading()
                        is ProfileState.Success -> {
                            hideLoading()
                            binding.profileModel = it.result
                        }
                        ProfileState.UnAuthorizedAccess -> {
                            hideLoading()
                            context?.toast(getString(R.string.err_http_auth))
                            viewModel.dispatchIntent(ProfileIntent.LogoutUser)
                        }
                        ProfileState.Logout -> {
                            navController.navigate(ProfileFragmentDirections.actionProfileFragmentToAuthFragment())
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.dispatchIntent(ProfileIntent.LogoutUser)
        return super.onOptionsItemSelected(item)
    }

}