package com.example.binsearch.ui.navigation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

fun goToPhoneApp(context: Context, phone: String, handleNavigationError: () -> Unit) {
    try {
        val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        context.startActivity(callIntent)
    } catch (e: ActivityNotFoundException) {
        handleNavigationError()
    }
}

fun goToBrowser(context: Context, url: String, handleNavigationError: () -> Unit) {
    try {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://$url"))
        context.startActivity(webIntent)
    } catch (e: ActivityNotFoundException) {
        handleNavigationError()
    }
}

fun goToMapApp(context: Context, coordinates: String, handleNavigationError: () -> Unit) {
    try {
        val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:$coordinates"))
        context.startActivity(mapIntent)
    } catch (e: ActivityNotFoundException) {
        handleNavigationError()
    }
}