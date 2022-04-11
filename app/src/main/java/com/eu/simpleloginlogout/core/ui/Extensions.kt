package com.eu.simpleloginlogout.core.ui

import android.content.Context
import android.widget.Toast

/**
 * shows toast using context
 */
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}