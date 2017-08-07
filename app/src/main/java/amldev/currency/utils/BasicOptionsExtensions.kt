package amldev.currency.utils

import android.content.Context
import android.net.ConnectivityManager


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