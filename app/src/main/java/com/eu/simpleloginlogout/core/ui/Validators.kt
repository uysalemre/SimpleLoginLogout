package com.eu.simpleloginlogout.core.ui

import android.content.Context
import com.eu.simpleloginlogout.R
import com.google.android.material.textfield.TextInputLayout

/**
 * @author Emre UYSAL
 * Validator extensions for TextInputLayout and login forms
 */
const val EMAIL_REGEX =
    "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"

fun TextInputLayout.validate(): Boolean {
    error = when (id) {
        R.id.tilAuthUsername -> validateUsername(context, editText?.text.toString())
        R.id.tilAuthPassword -> validatePassword(context, editText?.text.toString())
        else -> null
    }
    return error == null
}

fun TextInputLayout.getText() = editText!!.text.toString()

private fun validateUsername(context: Context, value: String?) = when {
    value.isNullOrEmpty() || !value.matches(EMAIL_REGEX.toRegex()) -> context.getString(
        R.string.auth_user_error,
        context.getString(R.string.auth_username_hint).lowercase()
    )
    else -> null
}

private fun validatePassword(context: Context, value: String?) = when {
    value.isNullOrEmpty() || value.length < 3 -> context.getString(
        R.string.auth_user_error,
        context.getString(R.string.auth_password_hint).lowercase()
    )
    else -> null
}