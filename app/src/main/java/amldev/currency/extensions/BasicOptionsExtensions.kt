package amldev.currency.extensions

import amldev.currency.R
import amldev.currency.data.Constants
import android.content.Context
import android.net.ConnectivityManager
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**************************************************************************************************
 * Created by Anartz Mugika on 7/8/17.
 * In this extension I add basic operations functions to use in all app
 *************************************************************************************************/
fun isNetworkConnected(context: Context): Boolean {
    /**************************************************************************************************
    * Retrieving an instance of the ConnectivityManager class from the current application context.
    * The ConnectivityManager class is simply your go to class if you need any information about the
    * state of network connectivity. It can also be set up to report network connection
    * changes to your application.
    **************************************************************************************************/
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    /**************************************************************************************************
     * Retrieving information about the current active network connection.
     * This will be null if there's no network connection.
    **************************************************************************************************/
    val networkInfo = connectivityManager.activeNetworkInfo // 2

    /**************************************************************************************************
     * Checking if the device is connected to an available network connection.
     **************************************************************************************************/
    return networkInfo != null && networkInfo.isConnected // 3
}

fun getDefaultShareIntent(context: Context, bm: Bitmap?): Intent {

    val intent = Intent(Intent.ACTION_SEND)
    if (bm == null) {
        intent.type = Constants.INTENT_TYPE_TEXT
    } else {
        intent.type = Constants.INTENT_TYPE_IMAGE
    }
    //

    val subject: String
    val text: String

    subject = context.resources.getString(R.string.share_chooser_title)

    text = context.resources.getString(R.string.share_message)


    if (bm != null) {
        // Save this bitmap to a file.
        val cache = context.externalCacheDir
        val sharefile = File(cache, Constants.SHARE_IMAGE_DEFAULT)
        try {
            val out = FileOutputStream(sharefile)
            bm.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("${Constants.URL_FILE_START}$sharefile"))
    }

    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    intent.putExtra(Intent.EXTRA_TEXT, text)

    //Start social networks window
    return Intent.createChooser(intent, context.resources.getString(R.string.share_chooser_title))
}