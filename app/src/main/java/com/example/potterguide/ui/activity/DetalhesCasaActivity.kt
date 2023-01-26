package com.example.potterguide.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.potterguide.R
import com.example.potterguide.databinding.ActivityDetalhesCasaBinding

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
            CHAVE_CASA_GRIFINORIA -> {
                binding.DetalheCasaImagem.setImageResource(R.drawable.gryffindor)
                binding.DetalheCasaTexto.text = getString(R.string.activity_detalhe_casa_detalhesGrifinoria)
                configuraFAB(CHAVE_PERSONAGENS_GRIFINORIA)
            }

            CHAVE_CASA_SONSERINA -> {
                binding.DetalheCasaImagem.setImageResource(R.drawable.slytherin)
                binding.DetalheCasaTexto.text = getString(R.string.activity_detalhe_casa_detalhesSonserina)
                configuraFAB(CHAVE_PERSONAGENS_SONSERINA)

            }

            CHAVE_CASA_LUFA_LUFA -> {
                binding.DetalheCasaImagem.setImageResource(R.drawable.hufflepuff)
                binding.DetalheCasaTexto.text = getString(R.string.activity_detalhe_casa_detalhesLufaLufa)
                configuraFAB(CHAVE_PERSONAGENS_LUFA_LUFA)

            }

            CHAVE_CASA_CORVINAL -> {
                binding.DetalheCasaImagem.setImageResource(R.drawable.ravenclaw)
                binding.DetalheCasaTexto.text = getString(R.string.activity_detalhe_casa_detalhesCorvinal)
                configuraFAB(CHAVE_PERSONAGENS_CORVINAL)


            }
        }
    }

    private fun configuraFAB(identificador: String) {
        binding.DetalheCasaActionButton.setOnClickListener {
//            vaiPara(PersonagensActivity::class.java) {
//                putExtra(CHAVE_TELA, identificador)
//            }
        }
    }

}

