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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.potterguide.R
import com.example.potterguide.databinding.FragmentFeiticosBinding
import com.example.potterguide.extensions.mostraBottomSheetDialog
import com.example.potterguide.extensions.mostraSnackBar
import com.example.potterguide.ui.fragment.recyclerview.adapter.ListaFeiticosAdapter
import com.example.potterguide.ui.viewModel.FeiticosViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeiticosFragment : Fragment() {

    private var _binding: FragmentFeiticosBinding? = null
    private val binding get() = _binding!!
    private val adapter: ListaFeiticosAdapter by inject()
    private val model: FeiticosViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeiticosBinding.inflate(inflater, container, false)
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
        buscaFeiticos()
    }

    private fun adicionaMenuProvider() {
        activity?.let {
            val menuHost: MenuHost = it
            menuHost.invalidateMenu()
            menuHost.addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.fragment_feiticos_menu, menu)
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
                activity?.getString(R.string.fragment_Feiticos_menuitem_searchView)
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
        val recyclerView = binding.feiticoFragmentRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        configuraBotaoScroll(recyclerView)
    }

    private fun configuraBotaoScroll(recyclerView: RecyclerView) {
        val botaoScroll = binding.feiticoFragmentFloatActionButtonRecyclerViewScroll
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
            val swipeRefresh = binding.feiticoFragmentSwipeRefresh
            swipeRefresh.setColorSchemeColors(it.getColor(R.color.amarelo))
            swipeRefresh.setProgressBackgroundColorSchemeColor(it.getColor(R.color.amarelo_escuro))
            swipeRefresh.setOnRefreshListener {
                lifecycleScope.launch {
                    mensagemFalha(false)
                    load(false)
                    model.buscaFeiticos()
                    binding.feiticoFragmentSwipeRefresh.isRefreshing = false
                }
            }
        }
    }

    private fun load(visivel: Boolean) {
        _binding?.let {
            binding.feiticoFragmentProgressBar.visibility = if (visivel) View.VISIBLE else View.GONE
        }
    }

    private fun buscaFeiticos() {
        configuraOberverFeiticos()
        lifecycleScope.launch {
            model.buscaFeiticos()
            load(false)

        }
    }

    private fun configuraOberverFeiticos() {
        model.listaDeFeiticos.observe(this@FeiticosFragment) { listaDeFeiicos ->
            model.sucesso = {
                adapter.submitList(listaDeFeiicos)
                mensagemFalha(false)
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

    private fun mensagemFalha(visivel: Boolean) {
        if (visivel) {
            binding.feiticoFragmentTextViewFalha.visibility = View.VISIBLE
            binding.feiticoFragmentImageViewFalha.visibility = View.VISIBLE
        } else {
            binding.feiticoFragmentTextViewFalha.visibility = View.GONE
            binding.feiticoFragmentImageViewFalha.visibility = View.GONE
        }
    }

    private fun mostraItens(visivel: Boolean) {
        binding.feiticoFragmentRecyclerView.visibility = if (visivel) View.VISIBLE else View.GONE
        binding.feiticoFragmentFloatActionButtonRecyclerViewScroll.visibility =
            if (visivel) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}