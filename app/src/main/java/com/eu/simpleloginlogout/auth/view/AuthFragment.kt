package com.eu.simpleloginlogout.auth.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.eu.simpleloginlogout.R
import com.eu.simpleloginlogout.auth.viewmodel.AuthViewModel
import com.eu.simpleloginlogout.auth.viewmodel.intent.AuthIntent
import com.eu.simpleloginlogout.auth.viewmodel.state.AuthState
import com.eu.simpleloginlogout.base.BaseFragment
import com.eu.simpleloginlogout.core.persistance.IDataStore
import com.eu.simpleloginlogout.core.ui.toast
import com.eu.simpleloginlogout.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Emre UYSAL
 * AuthFragment that shows login screen to user and validates to move profile page
 */
@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding>(R.layout.fragment_auth) {
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var dataStore: IDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkUserLoginStat()
    }

    override fun onViewCreationCompleted() {
        binding.viewModel = authViewModel
        registerObservingUIState()
    }

    private fun registerObservingUIState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.uiState.collect {
                    when (it) {
                        is AuthState.Error -> {
                            hideLoading()
                            context?.toast(getString(it.message))
                        }
                        AuthState.Init -> {}
                        AuthState.Loading -> showLoading()
                        is AuthState.Success -> {
                            hideLoading()
                            authViewModel.dispatchIntent(
                                AuthIntent.SaveUserToDataStore(
                                    it.result
                                )
                            )
                        }
                    }
                }
            }

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.navigationEvent.collect {
                    navigateToProfile()
                }
            }
        }
    }

    private fun checkUserLoginStat() {
        lifecycleScope.launch {
            dataStore.getUserFromPreferencesStore().collect {
                when {
                    it.token.isNotEmpty() && it.refreshToken.isNotEmpty() -> {
                        navigateToProfile()
                    }
                }
            }
        }
    }

    private fun navigateToProfile() =
        navController.navigate(AuthFragmentDirections.actionAuthFragmentToProfileFragment())

}