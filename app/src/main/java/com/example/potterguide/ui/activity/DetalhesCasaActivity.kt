package com.example.potterguide.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.potterguide.R
import com.example.potterguide.databinding.ActivityDetalhesCasaBinding
import com.example.potterguide.extensions.vaiPara

class DetalhesCasaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalhesCasaBinding.inflate(layoutInflater)
    }

    private var casa: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        buscaCasa()
    }

    private fun buscaCasa() {
        casa = intent.getStringExtra(CHAVE_CASA)
        casa?.let { buscaInformacoes(it) } ?: finish()
    }

    private fun buscaInformacoes(casa: String) {
        when (casa) {
            getString(R.string.grifinoria) -> {
                binding.DetalheCasaImagem.setImageResource(R.drawable.gryffindor)
                binding.DetalheCasaTexto.text = getString(R.string.detalhesGrifinoria)
                configuraFAB(getString(R.string.alunosGrifinoria))
            }

            getString(R.string.sonserina) -> {
                binding.DetalheCasaImagem.setImageResource(R.drawable.slytherin)
                binding.DetalheCasaTexto.text = getString(R.string.detalhesSonserina)
                configuraFAB(getString(R.string.alunosSonserina))

            }

            getString(R.string.lufaLufa) -> {
                binding.DetalheCasaImagem.setImageResource(R.drawable.hufflepuff)
                binding.DetalheCasaTexto.text = getString(R.string.detalhesLufaLufa)
                configuraFAB(getString(R.string.alunosLufalufa))

            }

            getString(R.string.corvinal) -> {
                binding.DetalheCasaImagem.setImageResource(R.drawable.ravenclaw)
                binding.DetalheCasaTexto.text = getString(R.string.detalhesCorvinal)
                configuraFAB(getString(R.string.alunosCorvinal))


            }
        }
    }

    private fun configuraFAB(identificador: String) {
        binding.DetalheCasaActionButton.setOnClickListener {
            vaiPara(PersonagensActivity::class.java) {
                putExtra(CHAVE_TELA, identificador)
            }
        }
    }

}

