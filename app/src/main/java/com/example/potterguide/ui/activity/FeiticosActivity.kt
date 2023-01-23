package com.example.potterguide.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.potterguide.R
import com.example.potterguide.databinding.ActivityFeiticosBinding
import com.example.potterguide.extensions.mostraSnackBar
import com.example.potterguide.ui.activity.recyclerview.adapter.ListaFeiticosAdapter
import com.example.potterguide.ui.viewModel.FeiticosViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeiticosActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFeiticosBinding.inflate(layoutInflater)
    }

    private val adapter: ListaFeiticosAdapter by inject()

    private val model: FeiticosViewModel by viewModel()


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
        val recyclerView = binding.recyclerViewFeiticos
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
    }

    private fun configuraSearchView() {
        val search = binding.searchViewFeiticos
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
        search.queryHint = getString(R.string.activity_Feiticos_buscarFeiticos)
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

    private fun load(visivel: Boolean) {
        binding.ProgressbarFeiticos.visibility = if (visivel) View.VISIBLE else View.GONE
    }

    private suspend fun atualiza() {
        mensagemFalha(false)
        mostraItens(false)
        model.buscaFeiticos()
        model.listaDeFeiticos.observe(this) { listaDeFeiicos ->
            if (listaDeFeiicos.isNotEmpty()) {
                adapter.submitList(listaDeFeiicos)
                mostraItens(true)
                mensagemFalha(false)
                model.erroAtualizacao = {
                    mostraSnackBar(binding.root, getString(R.string.common_erro_atualicao))
                }
            } else {
                mostraItens(false)
                mensagemFalha(true)
            }

        }

    }


    private fun mensagemFalha(visivel: Boolean) {
        if (visivel) {
            binding.TextoFalhaCarregamentoFeiticos.visibility = View.VISIBLE
            binding.imagemSemInternetFeiticos.visibility = View.VISIBLE
        } else {
            binding.TextoFalhaCarregamentoFeiticos.visibility = View.GONE
            binding.imagemSemInternetFeiticos.visibility = View.GONE
        }
    }

    private fun mostraItens(visivel: Boolean) {
        if (visivel) {
            binding.TextPrincipalFeiticos.visibility = View.VISIBLE
            binding.recyclerViewFeiticos.visibility = View.VISIBLE
            binding.searchViewFeiticos.visibility = View.VISIBLE
        } else {
            binding.searchViewFeiticos.visibility = View.GONE
            binding.TextPrincipalFeiticos.visibility = View.GONE
            binding.recyclerViewFeiticos.visibility = View.GONE
        }
    }

}