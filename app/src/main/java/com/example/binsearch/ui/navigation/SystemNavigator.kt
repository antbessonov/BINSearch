package com.example.binsearch.ui.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SystemNavigator @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun goToPhoneApp(phone: String) {
        val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        context.startActivity(callIntent)
    }

    fun goToBrowser(url: String) {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://$url"))
        context.startActivity(webIntent)
    }

    fun goToMapApp(coordinates: String) {
        val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:$coordinates"))
        context.startActivity(mapIntent)
    }
}