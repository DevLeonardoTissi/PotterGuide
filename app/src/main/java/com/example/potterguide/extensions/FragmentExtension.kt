package com.example.potterguide.extensions

import android.view.View
import androidx.fragment.app.Fragment
import com.example.potterguide.R
import com.google.android.material.snackbar.Snackbar

fun Fragment.mostraSnackBar(view: View, mensagem: String){
    activity?.let {
        Snackbar.make(
        view,
        mensagem,
        Snackbar.LENGTH_INDEFINITE
    )
        .setAction(R.string.common_ok) {

        }
        .setActionTextColor(it.getColor(R.color.white))
        .setBackgroundTint(it.getColor(R.color.amarelo_escuro))
        .show()
    }
}