package com.example.potterguide.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.potterguide.R
import com.example.potterguide.databinding.FragmentPersonagensBinding
import com.example.potterguide.extensions.dataStore
import com.example.potterguide.extensions.mostraBottomSheetDialog
import com.example.potterguide.extensions.mostraSnackBar
import com.example.potterguide.extensions.vaiPara
import com.example.potterguide.ui.activity.*
import com.example.potterguide.ui.fragment.recyclerview.adapter.ListaPersonagensAdapter
import com.example.potterguide.ui.viewModel.PersonagensViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PersonagensFragment : Fragment() {

    private var _binding: FragmentPersonagensBinding? = null
    private val binding get() = _binding!!
    private val adapter: ListaPersonagensAdapter by inject()
    private val model: PersonagensViewModel by viewModel()
    private var layoutGridRecyclerView = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonagensBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adicionaMenuProvider()
        configuraRecyclerView()
        configuraBottomNavigation()
        configuraSwipeRefresh()
        configuraBotaoRecarregar()
    }

    override fun onStart() {
        super.onStart()
        mensagemFalha(false)
        load(true)
        mostraItens(false)
        buscaPersonagens()
    }


    private fun adicionaMenuProvider() {
        activity?.let {
            val menuHost: MenuHost = it
            menuHost.invalidateMenu()
            menuHost.addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.fragment_personagens_menu, menu)
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
        activity?.let { fragmentActivity ->
            val recyclerView = binding.personagemFragmentRecyclerView
            val botaoAlteraLayout = binding.personagemFragmentFloatActionButtonRecyclerViewLayout
            recyclerView.adapter = adapter
            buscaPreferencesLayoutRecyclerView(fragmentActivity, recyclerView, botaoAlteraLayout)
            configuraBotaoAlteraLayout(botaoAlteraLayout, recyclerView, fragmentActivity)
            configuraBotaoScroll(recyclerView)
            adapter.quandoClicaNoItem = { personagem ->
                fragmentActivity.vaiPara(DetalhesPersonagemActivity::class.java) {
                    putExtra(CHAVE_PERSONAGEM, personagem)
                }
            }
        }
    }

    private fun configuraBotaoScroll(recyclerView: RecyclerView) {
        val botaoScroll = binding.personagemFragmentFloatActionButtonRecyclerViewScroll
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

    private fun buscaPreferencesLayoutRecyclerView(
        fragmentActivity: FragmentActivity,
        recyclerView: RecyclerView,
        botaoAlteraLayout: FloatingActionButton
    ) {
        lifecycleScope.launch {
            fragmentActivity.dataStore.data.collect { preferences ->
                preferences[booleanPreferencesKey("layoutRecyclerView")]?.let { layoutPreference ->
                    layoutGridRecyclerView = layoutPreference

                    if (layoutGridRecyclerView) {
                        layoutgrid(true, recyclerView, botaoAlteraLayout)

                    } else {
                        layoutgrid(false, recyclerView, botaoAlteraLayout)

                    }
                } ?: layoutgrid(false, recyclerView, botaoAlteraLayout)

            }
        }
    }

    private fun configuraBotaoAlteraLayout(
        botaoAlteraLayout: FloatingActionButton,
        recyclerView: RecyclerView,
        fragmentActivity: FragmentActivity
    ) {
        botaoAlteraLayout.setOnClickListener {
            layoutGridRecyclerView = if (layoutGridRecyclerView) {
                layoutgrid(false, recyclerView, botaoAlteraLayout)
                false

            } else {
                layoutgrid(true, recyclerView, botaoAlteraLayout)
                true
            }

            salvaPreferences(fragmentActivity)
        }
    }


    private fun layoutgrid(
        isGrid: Boolean,
        recyclerView: RecyclerView,
        botaoAlteraLayout: FloatingActionButton
    ) {
        if (isGrid) {
            recyclerView.layoutManager = GridLayoutManager(context, 3)
            botaoAlteraLayout.setImageResource(R.drawable.ic_list)
            adapter.mostraNomeEEscola = false
        } else {
            recyclerView.layoutManager = LinearLayoutManager(context)
            adapter.mostraNomeEEscola = true
            botaoAlteraLayout.setImageResource(R.drawable.ic_grid)
        }

    }

    private fun salvaPreferences(fragmentActivity: FragmentActivity) {
        lifecycleScope.launch {
            fragmentActivity.dataStore.edit { preferences ->
                preferences[booleanPreferencesKey("layoutRecyclerView")] =
                    layoutGridRecyclerView
            }
        }
    }


    private fun configuraBottomNavigation() {
        view?.let {
            val bottonNavigation =
                it.findViewById<BottomNavigationView>(R.id.personagemFragment_bottomNavigation)


            bottonNavigation.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {

                    R.id.menuItem_grifinoria -> {
                        model.setIdentificador(PERSONAGENS_GRIFINORIA)
                    }
                    R.id.menuItem_sonserina -> {
                        model.setIdentificador(PERSONAGENS_SONSERINA)
                    }
                    R.id.menuItem_corvinal -> {
                        model.setIdentificador(PERSONAGENS_CORVINAL)
                    }
                    R.id.menuItem_lufalufa -> {
                        model.setIdentificador(PERSONAGENS_LUFA_LUFA)
                    }

                    R.id.menuItem_todos -> {
                        model.setIdentificador(TODOS_OS_PERSONAGENS)
                    }

                    else -> {}
                }
                true
            }

        }

    }

    private fun configuraSwipeRefresh() {
        activity?.let {
            val swipeRefresh = binding.personagemFragmentSwipeRefresh
            swipeRefresh.setColorSchemeColors(it.getColor(R.color.amarelo))
            swipeRefresh.setProgressBackgroundColorSchemeColor(it.getColor(R.color.amarelo_escuro))
            swipeRefresh.setOnRefreshListener {
                lifecycleScope.launch {
                    load(false)
                    mensagemFalha(false)
                    model.buscaPersonagens()
                    binding.personagemFragmentSwipeRefresh.isRefreshing = false
                }

            }
        }

    }

    private fun load(visivel: Boolean) {
        _binding?.let {
            binding.personagemFragmentProgressBar.visibility =
                if (visivel) View.VISIBLE else View.GONE
        }
    }

    private fun buscaPersonagens() {
        configuraObserverPersonagens()
        model.identificador.observe(this@PersonagensFragment) {
            lifecycleScope.launch {
                model.buscaPersonagens()
                load(false)
            }
        }
    }

    private fun configuraObserverPersonagens() {
        model.listaDePersonagens.observe(this@PersonagensFragment) { lista ->
            model.sucesso = {
                load(false)
                mensagemFalha(false)
                adapter.submitList(lista)
                mostraItens(true)
            }

            model.erroAtualizacao = {
                mostraItens(true)
                activity?.mostraSnackBar(binding.root, getString(R.string.common_erro_atualicao))
            }

            model.erro = {
                mensagemFalha(true)
                mostraItens(false)
                load(false)
            }
        }
    }

    private fun mensagemFalha(visivel: Boolean) {
        if (visivel) {
            binding.personagensFragmentButtonFalha.visibility = View.VISIBLE
            binding.personagemFragmentTextViewFalha.visibility = View.VISIBLE
            binding.personagemFragmentImageViewFalha.visibility = View.VISIBLE
        } else {
            binding.personagemFragmentTextViewFalha.visibility = View.GONE
            binding.personagemFragmentImageViewFalha.visibility = View.GONE
            binding.personagensFragmentButtonFalha.visibility = View.GONE
        }
    }

    private fun configuraBotaoRecarregar() {
        val botaoCarregar = binding.personagensFragmentButtonFalha
        botaoCarregar.setOnClickListener {
            lifecycleScope.launch {
                mensagemFalha(false)
                load(true)
                model.buscaPersonagens()
            }
        }
    }

    private fun mostraItens(visivel: Boolean) {
        binding.personagemFragmentRecyclerView.visibility = if (visivel) View.VISIBLE else View.GONE
        binding.personagemFragmentFloatActionButtonRecyclerViewLayout.visibility =
            if (visivel) View.VISIBLE else View.GONE
        binding.personagemFragmentFloatActionButtonRecyclerViewScroll.visibility =
            if (visivel) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


