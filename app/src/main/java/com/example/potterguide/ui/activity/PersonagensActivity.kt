package com.example.potterguide.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.potterguide.databinding.ActivityPersonagensBinding
import com.example.potterguide.extensions.vaiPara
import com.example.potterguide.model.Personagem
import com.example.potterguide.ui.activity.recyclerview.adapter.ListaPersonagensAdapter
import com.example.potterguide.webclient.RetrofitInicializador
import com.example.potterguide.webclient.model.PersonagemResposta
import com.example.potterguide.webclient.services.HarryPotterService
import kotlinx.coroutines.launch

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
        configuraSwipeRefresh()


        lifecycleScope.launch {
             mostraload(true)
            buscaTodos()
             mostraload(false)
        }
    }

    private suspend fun buscaTodos() {
        try {
            escondeItens()
            val listapersonagem = tentaBuscar()
            adapter.atualiza(listapersonagem)
            mostraItens()
        } catch (e: Exception) {
            Log.e("TAG", "busca: ", e)
        }
    }

    private fun mostraload(ativado: Boolean) {
       if (ativado){
           binding.ProgressbarPesonagens.visibility = View.VISIBLE
       }else{
           binding.ProgressbarPesonagens.visibility = View.GONE
       }
    }

    private fun mostraItens() {
        binding.TextPrincipalPersonagens.visibility = View.VISIBLE
        binding.recyclerViewPersonagens.visibility = View.VISIBLE
    }

    private fun escondeItens() {
        binding.TextPrincipalPersonagens.visibility = View.GONE
        binding.recyclerViewPersonagens.visibility = View.GONE
    }

    private suspend fun tentaBuscar(): List<Personagem> {
        val personagensResposta = identificaLista(texto.toString())
        val listapersonagem = personagensResposta.map { personagensResposta ->
            personagensResposta.personagem
        }
        return listapersonagem
    }

    private fun configuraRecyclerView() {
        binding.recyclerViewPersonagens.adapter = adapter
        adapter.quandoClicaNoItem = {
            vaiPara(DetalhesPersonagemActivity::class.java) {
                putExtra(CHAVE_PERSONAGEM, it)
            }
        }
    }

    private fun buscaTexto() {
        texto = intent.getStringExtra(CHAVE_TELA)
        texto?.let { trocaTexto(it) } ?: finish()
    }

    private fun trocaTexto(texto: String) {
        val textoPrincipal = binding.TextPrincipalPersonagens
        textoPrincipal.setText(texto)
    }

    suspend fun identificaLista(texto: String): List<PersonagemResposta> {

        val harrypotterservice: HarryPotterService = RetrofitInicializador().harryPotterService

        when (texto) {
            "Todos os personagens" -> {
                return harrypotterservice.buscaTodos()
            }
            "Alunos" -> {
                return harrypotterservice.buscaTodosAlunos()
            }
            "Funcionários" -> return harrypotterservice.buscaTodosFuncionarios()
        }

        return emptyList()

    }

    private fun configuraSwipeRefresh(){
        binding.SwiperefreshPersonagens.setOnRefreshListener {
            lifecycleScope.launch {
                buscaTodos()
                binding.SwiperefreshPersonagens.isRefreshing = false
            }
        }
    }

}

