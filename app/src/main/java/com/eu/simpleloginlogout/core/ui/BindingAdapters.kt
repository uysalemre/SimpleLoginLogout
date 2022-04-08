package com.eu.simpleloginlogout.core.ui

import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.eu.simpleloginlogout.R
import com.eu.simpleloginlogout.auth.viewmodel.AuthViewModel
import com.eu.simpleloginlogout.auth.viewmodel.intent.AuthIntent
import com.eu.simpleloginlogout.profile.viewmodel.ProfileViewModel
import com.eu.simpleloginlogout.profile.viewmodel.intent.ProfileIntent
import com.google.android.material.textfield.TextInputLayout

/**
 * @author Emre UYSAL
 * Binding adapter that loads image using glide
 */
@BindingAdapter("loadImage")
fun loadImage(view: ImageView, photo: String?) {
    photo?.let {
        Glide.with(view.context)
            .load(it)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .error(R.drawable.ic_image_error)
            .placeholder(R.drawable.ic_image_background)
            .into(view)
    }
}

/**
 * @author Emre UYSAL
 * Binding adapter that validates and submits login form
 */
@BindingAdapter(value = ["tilUsername", "tilPassword", "authViewModel"], requireAll = true)
fun submitForm(
    view: Button,
    tilUsername: TextInputLayout,
    tilPassword: TextInputLayout,
    authViewModel: AuthViewModel
) {
    if (!view.hasOnClickListeners()) {
        view.setOnClickListener {
            if (tilUsername.validate() and tilPassword.validate()) {
                authViewModel.dispatchIntent(
                    AuthIntent.Submit(
                        tilUsername.getText(),
                        tilPassword.getText()
                    )
                )
            }
        }
    }
}

@BindingAdapter("setRefreshStatus")
fun setRefreshStatus(
    view: SwipeRefreshLayout,
    status: Boolean
) {
    view.isRefreshing = status
}

@BindingAdapter("onRefresh")
fun onRefresh(
    view: SwipeRefreshLayout,
    viewModel: ProfileViewModel
) {
    view.setOnRefreshListener {
        viewModel.dispatchIntent(ProfileIntent.SwipeAndRefresh)
    }
}