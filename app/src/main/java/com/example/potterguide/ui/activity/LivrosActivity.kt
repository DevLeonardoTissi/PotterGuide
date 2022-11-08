package com.example.potterguide.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.potterguide.R
import com.example.potterguide.databinding.ActivityLivrosBinding
import com.example.potterguide.repositorio.Repositorio
import com.example.potterguide.ui.activity.recyclerview.adapter.ListaLivrosAdapter
import com.example.potterguide.ui.dialogLivro.DialogDetalheLivro
import kotlinx.coroutines.launch

class LivrosActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityLivrosBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        ListaLivrosAdapter(this)
    }

    private val repositorio by lazy {
        Repositorio(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraSwipeRefresh()

        lifecycleScope.launch{
            mostraload(true)
            atualiza()
            mostraload(false)
        }
    }

    private suspend fun atualiza(){
        try {
            mostraMensagemDeFalha(false)
            escondeItens(true)
            adapter.atualiza(repositorio.buscaLivros())
            escondeItens(false)
        } catch (e: Exception) {
            Log.e("TAG", "atualiza: ", e)
            mostraMensagemDeFalha(true)
        }

    }

    private fun configuraRecyclerView(){
        val recyclerView = binding.recyclerViewLivros
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false )
        adapter.quandoClicaNoItem = {
            DialogDetalheLivro(this,it).mostra()
        }


        //  StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
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

    private fun mostraload(ativado: Boolean) {
        if (ativado) {
            binding.ProgressbarLivros.visibility = View.VISIBLE
        } else {
            binding.ProgressbarLivros.visibility = View.GONE
        }
    }

    private fun escondeItens(ativado: Boolean) {
        if (ativado) {
            binding.TextPrincipalLivros.visibility = View.GONE
            binding.recyclerViewLivros.visibility = View.GONE
        } else {
            binding.TextPrincipalLivros.visibility = View.VISIBLE
            binding.recyclerViewLivros.visibility = View.VISIBLE
        }
    }

    private fun mostraMensagemDeFalha(ativado: Boolean) {
        if (ativado) {
            binding.TextoFalhaCarregamentoLivros.visibility = View.VISIBLE
            binding.imagemSemInternetLivros.visibility = View.VISIBLE
        } else {
            binding.TextoFalhaCarregamentoLivros.visibility = View.GONE
            binding.imagemSemInternetLivros.visibility = View.GONE
        }
    }
}