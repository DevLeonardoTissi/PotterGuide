package com.example.potterguide.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.potterguide.databinding.FragmentCasasBinding
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
        configuraCardView()
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