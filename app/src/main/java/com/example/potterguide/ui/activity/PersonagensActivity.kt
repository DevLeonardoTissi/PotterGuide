package com.example.potterguide.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.potterguide.R
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
            atualiza()
            mostraload(false)
        }
    }

    private suspend fun atualiza() {
        try {
            mostraMensagemDeFalha(false)
            escondeItens(true)
            val listapersonagens = busca()
            adapter.atualiza(listapersonagens)
            escondeItens(false)
        } catch (e: Exception) {
            Log.e("TAG", "atualiza: ", e)
            mostraMensagemDeFalha(true)
        }
    }

    private fun mostraload(ativado: Boolean) {
        if (ativado) {
            binding.ProgressbarPesonagens.visibility = View.VISIBLE
        } else {
            binding.ProgressbarPesonagens.visibility = View.GONE
        }
    }

    private fun escondeItens(ativado: Boolean) {
        if (ativado) {
            binding.TextPrincipalPersonagens.visibility = View.GONE
            binding.recyclerViewPersonagens.visibility = View.GONE
        } else {
            binding.TextPrincipalPersonagens.visibility = View.VISIBLE
            binding.recyclerViewPersonagens.visibility = View.VISIBLE
        }
    }

    private fun mostraMensagemDeFalha(ativado: Boolean) {
        if (ativado) {
            binding.TextoFalhaCarregamentoPersonagens.visibility = View.VISIBLE
            binding.imagemSemInternetPersonagens.visibility = View.VISIBLE
        } else {
            binding.TextoFalhaCarregamentoPersonagens.visibility = View.GONE
            binding.imagemSemInternetPersonagens.visibility = View.GONE
        }
    }


    private suspend fun busca(): List<Personagem> {
        val listaResposta = identificaLista(texto.toString())
        val listapersonagem = listaResposta.map { personagensResposta ->
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
        textoPrincipal.text = texto
    }

    suspend fun identificaLista(texto: String): List<PersonagemResposta> {

        val harrypotterservice: HarryPotterService = RetrofitInicializador().harryPotterService

        when (texto) {
            getString(R.string.todosOsPersonagens) -> return harrypotterservice.buscaTodos()
            getString(R.string.alunos) -> return harrypotterservice.buscaTodosAlunos()
            getString(R.string.funcionarios) -> return harrypotterservice.buscaTodosFuncionarios()
            getString(R.string.alunosGrifinoria) -> return harrypotterservice.buscaTodosGrifinoria()
            getString(R.string.alunosSonserina) -> return harrypotterservice.buscaTodosSonserina()
            getString(R.string.alunosLufalufa) -> return harrypotterservice.buscaTodosLufaLufa()
            getString(R.string.alunosCorvinal) -> return harrypotterservice.buscaTodosCorvinal()

        }
        return emptyList()
    }

    private fun configuraSwipeRefresh() {
        binding.SwiperefreshPersonagens.setOnRefreshListener {
            lifecycleScope.launch {
                atualiza()
                binding.SwiperefreshPersonagens.isRefreshing = false
            }
        }
    }

}

