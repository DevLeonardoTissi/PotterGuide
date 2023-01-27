package com.example.potterguide.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.potterguide.R
import com.example.potterguide.databinding.FragmentPersonagensBinding
import com.example.potterguide.extensions.mostraSnackBar
import com.example.potterguide.ui.activity.*
import com.example.potterguide.ui.activity.recyclerview.adapter.ListaPersonagensAdapter
import com.example.potterguide.ui.viewModel.PersonagensViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PersonagensFragment : Fragment() {

    private var _binding: FragmentPersonagensBinding? = null
    private val binding get() = _binding!!

    private val adapter: ListaPersonagensAdapter by inject()

    private val model: PersonagensViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonagensBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraBottomNavigation()
        adicionaMenuProvider()
        configuraRecyclerView()
        configuraSwipeRefresh()
        lifecycleScope.launch {
            load(true)
            buscaPersonagens()
            load(false)
        }

    }

    private fun configuraBottomNavigation() {
        view?.let {
            val bottonNavigation =
                it.findViewById<BottomNavigationView>(R.id.personagemFragment_bottomNavigation)


            bottonNavigation.setOnItemSelectedListener {
                when (it.itemId) {

                    R.id.menuItem_grifinoria -> {
                        lifecycleScope.launch {
                            model.buscaPersonagens(CHAVE_PERSONAGENS_GRIFINORIA)
                        }
                    }
                    R.id.menuItem_sonserina -> {
                        lifecycleScope.launch {
                            model.buscaPersonagens(CHAVE_PERSONAGENS_SONSERINA)
                        }
                    }
                    R.id.menuItem_corvinal -> {
                        lifecycleScope.launch {
                            model.buscaPersonagens(CHAVE_PERSONAGENS_CORVINAL)
                        }
                    }
                    R.id.menuItem_lufalufa -> {
                        lifecycleScope.launch {
                            model.buscaPersonagens(CHAVE_PERSONAGENS_LUFA_LUFA)
                        }
                    }

                    R.id.menuItem_todos -> {
                        lifecycleScope.launch {
                            model.buscaPersonagens(CHAVE_TODOS_OS_PERSONAGENS)
                        }
                    }

                    else -> {}
                }
                true
            }

        }

    }

    private fun adicionaMenuProvider() {
        activity?.let {
            val menuHost: MenuHost = it
            menuHost.invalidateMenu()
            menuHost.addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.fragments_personagens_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                    return when (menuItem.itemId) {
                        R.id.searchView -> {
                            val searchView = menuItem.actionView as? SearchView
                            configuraSearchView(searchView)
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
                activity?.getString(R.string.fragment_Personagens_menuitem_searchView)

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
            val recyclerView = binding.personagemFragmentRecyclerView
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(it, 2)
//        adapter.quandoClicaNoItem = {
//            vaiPara(DetalhesPersonagemActivity::class.java) {
//                putExtra(CHAVE_PERSONAGEM, it)
//            }
//        }
        }
    }

    private fun configuraSwipeRefresh() {
        activity?.let {
            val swipeRefresh = binding.personagemFragmentSwipeRefresh
            swipeRefresh.setColorSchemeColors(it.getColor(R.color.amarelo))
            swipeRefresh.setProgressBackgroundColorSchemeColor(it.getColor(R.color.amarelo_escuro))
            swipeRefresh.setOnRefreshListener {
                lifecycleScope.launch {
                    buscaPersonagens()
                    binding.personagemFragmentSwipeRefresh.isRefreshing = false
                }
            }
        }

    }

    private fun load(visivel: Boolean) {
        binding.personagemFragmentProgressBar.visibility = if (visivel) View.VISIBLE else View.GONE
    }

    private suspend fun buscaPersonagens() {
        mensagemFalha(false)
        mostraItens(false)
        model.buscaPersonagens(CHAVE_TODOS_OS_PERSONAGENS)
        model.listaDePersonagens.observe(viewLifecycleOwner) { lista ->
            if (lista.isNotEmpty()) {
                adapter.submitList(lista)
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
            binding.personagemFragmentTextViewFalha.visibility = View.VISIBLE
            binding.personagemFragmentImageViewFalha.visibility = View.VISIBLE
        } else {
            binding.personagemFragmentTextViewFalha.visibility = View.GONE
            binding.personagemFragmentImageViewFalha.visibility = View.GONE
        }
    }

    private fun mostraItens(visivel: Boolean) {
        binding.personagemFragmentRecyclerView.visibility = if (visivel) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


