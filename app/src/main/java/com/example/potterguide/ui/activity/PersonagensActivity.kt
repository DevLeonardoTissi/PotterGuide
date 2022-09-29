package com.example.potterguide.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.potterguide.databinding.ActivityMainBinding
import com.example.potterguide.databinding.ActivityPersonagensBinding

class PersonagensActivity : AppCompatActivity() {

    private var texto: String? = null

    private val binding by lazy {
        ActivityPersonagensBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        buscaTexto()

    }

    private fun buscaTexto(){
        texto = intent.getStringExtra(CHAVE_TELA)
        texto?.let { trocaTexto(it) } ?: finish()
    }

    private fun trocaTexto(texto:String){
        val textoPrincipal = binding.TextPrincipal
        textoPrincipal.setText(texto)
    }
}

