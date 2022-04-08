package com.eu.simpleloginlogout.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.eu.simpleloginlogout.core.activity.viewmodel.MainActivityViewModel

/**
 * @author Emre UYSAL
 * Base Fragment for fragments
 * Manages binding lifecycle and creates general objects for children
 */
abstract class BaseFragment<db : ViewDataBinding>(@LayoutRes val layoutId: Int) : Fragment() {
    private var _binding: db? = null
    protected val binding get() = _binding!!
    protected val navController by lazy { findNavController() }
    private val activityViewModel by activityViewModels<MainActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreationCompleted()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected fun showLoading() = activityViewModel.isLoadingVisible(true)

    protected fun hideLoading() = activityViewModel.isLoadingVisible(false)

    abstract fun onViewCreationCompleted()
}