package com.example.potterguide.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.potterguide.R
import com.example.potterguide.databinding.ActivityFeiticosBinding
import com.example.potterguide.repositorio.Repositorio
import com.example.potterguide.ui.activity.recyclerview.adapter.ListaFeiticosAdapter
import kotlinx.coroutines.launch

class FeiticosActivity : AppCompatActivity(){

    private val binding by lazy {
        ActivityFeiticosBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        ListaFeiticosAdapter(this)
    }

    private val repositorio by lazy {
        Repositorio(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraSearchView()
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
            adapter.atualiza(repositorio.buscaFeiticos())
            escondeItens(false)
        } catch (e: Exception) {
            Log.e("TAG", "atualiza: ", e)
            mostraMensagemDeFalha(true)
        }

    }

    private fun configuraRecyclerView(){
        val recyclerView = binding.recyclerViewFeiticos
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)

        //   LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false )

    }

    private fun configuraSwipeRefresh() {
        val swipeRefresh = binding.SwiperefreshFeiticos
        swipeRefresh.setColorSchemeColors(getColor(R.color.Verde_principal))
        swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                atualiza()
                binding.SwiperefreshFeiticos.isRefreshing = false
            }
        }
    }

    private fun mostraload(ativado: Boolean) {
        if (ativado) {
            binding.ProgressbarFeiticos.visibility = View.VISIBLE
        } else {
            binding.ProgressbarFeiticos.visibility = View.GONE
        }
    }

    private fun escondeItens(ativado: Boolean) {
        if (ativado) {
            binding.searchViewFeiticos.visibility = View.GONE
            binding.TextPrincipalFeiticos.visibility = View.GONE
            binding.recyclerViewFeiticos.visibility = View.GONE
        } else {
            binding.TextPrincipalFeiticos.visibility = View.VISIBLE
            binding.recyclerViewFeiticos.visibility = View.VISIBLE
            binding.searchViewFeiticos.visibility = View.VISIBLE
        }
    }

    private fun mostraMensagemDeFalha(ativado: Boolean) {
        if (ativado) {
            binding.TextoFalhaCarregamentoFeiticos.visibility = View.VISIBLE
            binding.imagemSemInternetFeiticos.visibility = View.VISIBLE
        } else {
            binding.TextoFalhaCarregamentoFeiticos.visibility = View.GONE
            binding.imagemSemInternetFeiticos.visibility = View.GONE
        }
    }

    private fun configuraSearchView(){
        val search = binding.searchViewFeiticos
        search.isSubmitButtonEnabled = false
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                adapter.search(query)
                return true
            }

        })
        search.queryHint =  getString(R.string.buscarFeiticos)


    }

}