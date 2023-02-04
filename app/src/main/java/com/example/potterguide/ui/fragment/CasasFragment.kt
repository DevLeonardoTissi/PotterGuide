package com.example.potterguide.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.potterguide.R
import com.example.potterguide.databinding.FragmentCasasBinding
import com.example.potterguide.extensions.mostraBottomSheetDialog
import com.example.potterguide.extensions.vaiPara
import com.example.potterguide.ui.activity.*

class CasasFragment : Fragment() {

    private var _binding: FragmentCasasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCasasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adicionaMenuProvider()
        configuraCardView()
    }

    private fun adicionaMenuProvider() {
        activity?.let {
            val menuHost: MenuHost = it
            menuHost.invalidateMenu()
            menuHost.addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.fragment_casas_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                    return when (menuItem.itemId) {
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

    private fun configuraCardView() {
        binding.CardViewCasaGryffindor.setOnClickListener {
            activity?.vaiPara(DetalhesCasaActivity::class.java) {
                putExtra(CHAVE_CASA, CASA_GRIFINORIA)
            }
        }

        binding.CardViewCasaHufflepuff.setOnClickListener {
            activity?.vaiPara(DetalhesCasaActivity::class.java) {
                putExtra(CHAVE_CASA, CASA_LUFA_LUFA)
            }
        }

        binding.CardViewCasaRavenclaw.setOnClickListener {
            activity?.vaiPara(DetalhesCasaActivity::class.java) {
                putExtra(CHAVE_CASA, CASA_CORVINAL)
            }
        }

        binding.CardViewCasaSlytherin.setOnClickListener {
            activity?.vaiPara(DetalhesCasaActivity::class.java) {
                putExtra(CHAVE_CASA, CASA_SONSERINA)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}