package com.example.potterguide.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.potterguide.R
import com.example.potterguide.databinding.FragmentLivrosBinding
import com.example.potterguide.extensions.mostraBottomSheetDialog
import com.example.potterguide.extensions.mostraSnackBar
import com.example.potterguide.ui.dialog.DialogDetalheLivro
import com.example.potterguide.ui.fragment.recyclerview.adapter.ListaLivrosAdapter
import com.example.potterguide.ui.viewModel.LivrosViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class LivrosFragment : Fragment() {
    private var _binding: FragmentLivrosBinding? = null
    private val binding get() = _binding!!
    private val adapter: ListaLivrosAdapter by inject()
    private val model: LivrosViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLivrosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adicionaMenuProvider()
        configuraRecyclerView()
        configuraSwipeRefresh()
    }

    override fun onStart() {
        super.onStart()
        mensagemFalha(false)
        load(true)
        mostraItens(false)
        buscaLivros()
    }

    private fun adicionaMenuProvider() {
        activity?.let {
            val menuHost: MenuHost = it
            menuHost.invalidateMenu()
            menuHost.addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.fragment_livros_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                    return when (menuItem.itemId) {
                        R.id.searchView -> {
                            val searchView = menuItem.actionView as? SearchView
                            configuraSearchView(searchView)
                            true
                        }

                        R.id.sobre -> {
                            mostraBottomSheetDialog()
                            true
                        }
                        else -> false
                    }
                }
            }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        }
    }

    private fun configuraSearchView(searchView: SearchView?) {
        searchView?.let {
            searchView.isSubmitButtonEnabled = false
            searchView.queryHint =
                activity?.getString(R.string.fragment_Livros_menuitem_searchView)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        adapter.submitList(model.search(newText))
                    }
                    return true
                }
            })
        }
    }

    private fun configuraRecyclerView() {
        activity?.let {
            val recyclerView = binding.livroFragmentRecyclerView
            recyclerView.adapter = adapter
            recyclerView.layoutManager =
                LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
            adapter.quandoClicaNoItem = { livro ->
                DialogDetalheLivro(it, livro).mostra()
            }
            configuraBotaoScroll(recyclerView)
        }
    }

    private fun configuraBotaoScroll(recyclerView: RecyclerView) {
        val botaoScroll = binding.livroFragmentFloatActionButtonRecyclerViewScroll
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    botaoScroll.visibility = View.VISIBLE
                } else {
                    botaoScroll.visibility = View.GONE
                }
            }
        })
        botaoScroll.setOnClickListener {
            val layoutmanager = recyclerView.layoutManager
            layoutmanager?.smoothScrollToPosition(recyclerView, null, 0)
        }
    }

    private fun configuraSwipeRefresh() {
        activity?.let {
            val swipeRefresh = binding.livroFragmentSwipeRefresh
            swipeRefresh.setColorSchemeColors(it.getColor(R.color.amarelo))
            swipeRefresh.setProgressBackgroundColorSchemeColor(it.getColor(R.color.amarelo_escuro))
            swipeRefresh.setOnRefreshListener {
                lifecycleScope.launch {
                    mensagemFalha(false)
                    load(false)
                    model.buscaLivros()
                    binding.livroFragmentSwipeRefresh.isRefreshing = false
                }

            }
        }
    }

    private fun load(visivel: Boolean) {
        _binding?.let {
            binding.livroFragmentProgressBar.visibility = if (visivel) View.VISIBLE else View.GONE
        }
    }

    private fun buscaLivros() {
        configuraObserverLivros()
        lifecycleScope.launch {
            model.buscaLivros()
            load(false)
        }
    }

    private fun configuraObserverLivros() {
        model.listaDeLivros.observe(this@LivrosFragment) { listaDeLivros ->
            model.sucesso = {
                mensagemFalha(false)
                adapter.submitList(listaDeLivros)
                mostraItens(true)
            }
            model.erroAtualizacao = {
                mostraItens(true)
                activity?.mostraSnackBar(binding.root, getString(R.string.common_erro_atualicao))
            }

            model.erro = {
                mensagemFalha(true)
                mostraItens(false)
            }
        }
    }

    private fun mensagemFalha(ativado: Boolean) {
        if (ativado) {
            binding.livroFragmentTextViewFalha.visibility = View.VISIBLE
            binding.livroFragmentImageViewFalha.visibility = View.VISIBLE
        } else {
            binding.livroFragmentTextViewFalha.visibility = View.GONE
            binding.livroFragmentImageViewFalha.visibility = View.GONE
        }
    }

    private fun mostraItens(visivel: Boolean) {
        binding.livroFragmentRecyclerView.visibility = if (visivel) View.VISIBLE else View.GONE
        binding.livroFragmentFloatActionButtonRecyclerViewScroll .visibility = if (visivel) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}