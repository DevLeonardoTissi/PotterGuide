package com.example.potterguide.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.example.potterguide.R
import com.example.potterguide.databinding.ActivityPersonagensBinding
import com.example.potterguide.extensions.vaiPara
import com.example.potterguide.repositorio.Repositorio
import com.example.potterguide.ui.activity.recyclerview.adapter.ListaPersonagensAdapter
import kotlinx.coroutines.launch

class PersonagensActivity : AppCompatActivity(){

    private var identificador: String? = null

    private val binding by lazy {
        ActivityPersonagensBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        ListaPersonagensAdapter(this)
    }

    private val repositorio by lazy {
        Repositorio(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        buscaTexto()
        configuraSearchView()
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
            val listapersonagens = repositorio.buscaPersonagens(identificador.toString())
            adapter.atualiza(listapersonagens)
            escondeItens(false)
        } catch (e: Exception) {
            Log.e("TAG", "atualiza: ", e)
            mostraMensagemDeFalha(true)
        }
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
        identificador = intent.getStringExtra(CHAVE_TELA)
       identificador?.let { trocaTexto(it) } ?: finish()
    }

    private fun trocaTexto(texto: String) {
        val textoPrincipal = binding.TextPrincipalPersonagens
        textoPrincipal.text = texto
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

    private fun mostraload(ativado: Boolean) {
        if (ativado) {
            binding.ProgressbarPesonagens.visibility = View.VISIBLE
        } else {
            binding.ProgressbarPesonagens.visibility = View.GONE
        }
    }

    private fun escondeItens(ativado: Boolean) {
        if (ativado) {
            binding.searchViewPersonagens.visibility = View.GONE
            binding.TextPrincipalPersonagens.visibility = View.GONE
            binding.recyclerViewPersonagens.visibility = View.GONE
        } else {
            binding.TextPrincipalPersonagens.visibility = View.VISIBLE
            binding.recyclerViewPersonagens.visibility = View.VISIBLE
            binding.searchViewPersonagens.visibility = View.VISIBLE
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

 private fun configuraSearchView(){
     val search = binding.searchViewPersonagens
     search.isSubmitButtonEnabled = false
     search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
         override fun onQueryTextSubmit(query: String): Boolean {
             return false
         }

         override fun onQueryTextChange(query:String): Boolean {
             adapter.search(query)
             return true
         }
     })
     search.queryHint =  getString(R.string.buscarPersonagens)

 }



}

