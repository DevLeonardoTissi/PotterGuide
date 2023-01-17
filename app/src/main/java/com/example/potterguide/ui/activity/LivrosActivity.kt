package com.example.potterguide.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.potterguide.R
import com.example.potterguide.databinding.ActivityLivrosBinding
import com.example.potterguide.ui.activity.recyclerview.adapter.ListaLivrosAdapter
import com.example.potterguide.ui.dialogLivro.DialogDetalheLivro
import com.example.potterguide.ui.viewModel.LivrosViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LivrosActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityLivrosBinding.inflate(layoutInflater)
    }

    private val adapter: ListaLivrosAdapter by inject()

    private val model: LivrosViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraSearchView()
        configuraSwipeRefresh()

        lifecycleScope.launch {
            load(true)
            atualiza()
            load(false)
        }
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.recyclerViewLivros
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.quandoClicaNoItem = {
            DialogDetalheLivro(this, it).mostra()
        }

    }

    private fun configuraSearchView() {
        val search = binding.searchViewLivros
        search.isSubmitButtonEnabled = false
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let {
                    adapter.submitList(model.search(query))
                }
                return true
            }

        })
        search.queryHint = getString(R.string.activity_livros_buscarLivros)

    }

    private fun configuraSwipeRefresh() {
        val swipeRefresh = binding.SwiperefreshLivros
        swipeRefresh.setColorSchemeColors(getColor(R.color.Verde_principal))
        swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                atualiza()
                binding.SwiperefreshLivros.isRefreshing = false
            }
        }
    }

    private fun load(visivel: Boolean) {
        binding.ProgressbarLivros.visibility = if (visivel) View.VISIBLE else View.GONE
    }

    private suspend fun atualiza() {
        mensagemFalha(false)
        mostraItens(false)
        model.buscaLivros()
        model.listaDeLivros.observe(this) { listaDeLivros ->
            if (listaDeLivros.isNotEmpty()) {
                adapter.submitList(listaDeLivros)
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

    private fun mensagemFalha(ativado: Boolean) {
        if (ativado) {
            binding.TextoFalhaCarregamentoLivros.visibility = View.VISIBLE
            binding.imagemSemInternetLivros.visibility = View.VISIBLE
        } else {
            binding.TextoFalhaCarregamentoLivros.visibility = View.GONE
            binding.imagemSemInternetLivros.visibility = View.GONE
        }
    }

    private fun mostraItens(ativado: Boolean) {
        if (ativado) {
            binding.TextPrincipalLivros.visibility = View.VISIBLE
            binding.recyclerViewLivros.visibility = View.VISIBLE
            binding.searchViewLivros.visibility = View.VISIBLE
        } else {
            binding.searchViewLivros.visibility = View.GONE
            binding.TextPrincipalLivros.visibility = View.GONE
            binding.recyclerViewLivros.visibility = View.GONE
        }
    }

}