package com.example.potterguide.extensions

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.example.potterguide.R
import com.example.potterguide.databinding.BottomSheetDialogSobreBinding
import com.example.potterguide.ui.activity.LINKGITHUB
import com.example.potterguide.ui.activity.LINKGITHUBPOTTERGUIDE
import com.example.potterguide.ui.activity.LINKLINKEDIN
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.mostraBottomSheetDialog() {
    context?.let {
        val bottonSheetDialog = BottomSheetDialog(it, R.style.BottonSheetDialog)
        BottomSheetDialogSobreBinding.inflate(LayoutInflater.from(it)).apply {
            chipGitHub.setOnClickListener {
                vaiParaUri(LINKGITHUB)
            }

            chipLinkedin.setOnClickListener {
                vaiParaUri(LINKLINKEDIN)
            }

            chipGitHubPotterGuide.setOnClickListener {
                vaiParaUri(LINKGITHUBPOTTERGUIDE)
            }

            bottonSheetDialog.setContentView(root)
            bottonSheetDialog.show()
        }
    }
}

private fun Fragment.vaiParaUri(endereco: String) {
    val uri = Uri.parse(endereco)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    startActivity(intent)
}
