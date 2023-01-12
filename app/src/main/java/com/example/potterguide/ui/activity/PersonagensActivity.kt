package com.example.potterguide.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.potterguide.R
import com.example.potterguide.databinding.ActivityPersonagensBinding
import com.example.potterguide.extensions.vaiPara
import com.example.potterguide.ui.activity.recyclerview.adapter.ListaPersonagensAdapter
import com.example.potterguide.ui.viewModel.PersonagensViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PersonagensActivity : AppCompatActivity() {

    private lateinit var identificador: String

    private val binding by lazy {
        ActivityPersonagensBinding.inflate(layoutInflater)
    }

    private val adapter: ListaPersonagensAdapter by inject()

    private val model: PersonagensViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        buscaIdentificador()
        configuraSearchView()
        configuraRecyclerView()
        configuraSwipeRefresh()

        lifecycleScope.launch {
            load(true)
            atualiza()
            load(false)
        }
    }

    private fun buscaIdentificador() {
        intent.getStringExtra(CHAVE_TELA)?.let {
            identificador = it
            trocaTexto(identificador)
        } ?: finish()
    }

    private fun trocaTexto(texto: String) {
        val textoPrincipal = binding.TextPrincipalPersonagens
        textoPrincipal.text = texto
    }

    private fun configuraSearchView() {

        val search = binding.searchViewPersonagens
        search.isSubmitButtonEnabled = false
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let {
                    adapter.submitList(model.search(query))
                }
                return true
            }
        })
        search.queryHint = getString(R.string.activity_Personagens_buscarPersonagens)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.recyclerViewPersonagens
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter.quandoClicaNoItem = {
            vaiPara(DetalhesPersonagemActivity::class.java) {
                putExtra(CHAVE_PERSONAGEM, it)
            }
        }
    }

    private fun configuraSwipeRefresh() {
        val swipeRefresh = binding.SwiperefreshPersonagens
        swipeRefresh.setColorSchemeColors(getColor(R.color.Verde_principal))
        swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                atualiza()
                binding.SwiperefreshPersonagens.isRefreshing = false
            }
        }
    }

    private fun load(visivel: Boolean) {
        binding.ProgressbarPesonagens.visibility = if (visivel) View.VISIBLE else View.GONE
    }

    private suspend fun atualiza() {
        mensagemFalha(false)
        mostraItens(false)
        model.buscaPersonagens(identificador)
        model.listaDePersonagens.observe(this) { lista ->
            if (lista.isNotEmpty()) {
                adapter.submitList(lista)
                mostraItens(true)
                mensagemFalha(false)
                model.erroAtualizacao = {
                    snackbarErro()
                }
            } else {
                mostraItens(false)
                mensagemFalha(true)
            }
        }

    }

    private fun snackbarErro() {
        Snackbar.make(
            binding.root,
            getString(R.string.common_erro_atualicao),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(R.string.common_ok) {

            }
            .setActionTextColor(getColor(R.color.white))
            .setBackgroundTint(getColor(R.color.amarelo_escuro))
            .show()
    }

    private fun mensagemFalha(visivel: Boolean) {
        if (visivel) {
            binding.TextoFalhaCarregamentoPersonagens.visibility = View.VISIBLE
            binding.imagemSemInternetPersonagens.visibility = View.VISIBLE
        } else {
            binding.TextoFalhaCarregamentoPersonagens.visibility = View.GONE
            binding.imagemSemInternetPersonagens.visibility = View.GONE
        }
    }

    private fun mostraItens(visivel: Boolean) {
        if (visivel) {
            binding.TextPrincipalPersonagens.visibility = View.VISIBLE
            binding.recyclerViewPersonagens.visibility = View.VISIBLE
            binding.searchViewPersonagens.visibility = View.VISIBLE
        } else {
            binding.searchViewPersonagens.visibility = View.GONE
            binding.TextPrincipalPersonagens.visibility = View.GONE
            binding.recyclerViewPersonagens.visibility = View.GONE
        }
    }
}

