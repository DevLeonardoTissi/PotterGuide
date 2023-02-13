package com.example.potterguide.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun checaConexao(context: Context): Boolean {
    val gerenciadorDeConectividade =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = gerenciadorDeConectividade.activeNetwork ?: return false
    val activeNetwork =
        gerenciadorDeConectividade.getNetworkCapabilities(network) ?: return false

    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
    }
}