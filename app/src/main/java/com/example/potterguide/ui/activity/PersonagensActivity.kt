package com.example.potterguide.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.potterguide.databinding.ActivityMainBinding
import com.example.potterguide.databinding.ActivityPersonagensBinding
import com.example.potterguide.model.Personagem

import com.example.potterguide.ui.activity.recyclerview.adapter.ListaPersonagensAdapter
import com.example.potterguide.webclient.RetrofitInicializador
import com.example.potterguide.webclient.model.PersonagemResposta
import com.example.potterguide.webclient.services.HarryPotterService
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class PersonagensActivity : AppCompatActivity() {

    private var texto: String? = null

    private val binding by lazy {
        ActivityPersonagensBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        ListaPersonagensAdapter(this)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        buscaTexto()
        configuraRecyclerView()

        lifecycleScope.launch{
            val harrypotterservice: HarryPotterService = RetrofitInicializador().harryPotterService
            val personagensResposta = harrypotterservice.buscaTodos()
          val listapersonagem =  personagensResposta.map { personagensResposta ->
                personagensResposta.personagem
            }
            adapter.atualiza(listapersonagem)
        }
    }

    private fun configuraRecyclerView() {
        binding.recyclerViewPersonagens.adapter = adapter
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

