package com.example.potterguide.extensions

import android.app.Activity
import android.view.View
import com.example.potterguide.R
import com.google.android.material.snackbar.Snackbar

fun Activity.mostraErro(view: View){
    Snackbar.make(
        view,
        getString(R.string.common_erro_atualicao),
        Snackbar.LENGTH_INDEFINITE
    )
        .setAction(R.string.common_ok) {

        }
        .setActionTextColor(getColor(R.color.white))
        .setBackgroundTint(getColor(R.color.amarelo_escuro))
        .show()
}