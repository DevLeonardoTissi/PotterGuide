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
        configuraToolbar()

    }

    private fun configuraToolbar() {
        val toolbar = binding.detalhesCasaActivityToolbar
        toolbar.title =
            when (casa) {
                CASA_GRIFINORIA -> getString(R.string.activity_detalhes_casa_grifinoria)
                CASA_SONSERINA -> getString(R.string.activity_detalhes_casa_sonserina)
                CASA_LUFA_LUFA -> getString(R.string.activity_detalhes_casa_lufalufa)

                else -> getString(R.string.activity_detalhes_casa_corvinal)
            }
        toolbar.setNavigationOnClickListener {
            finish()
        }


    }

    private fun buscaCasa() {
        casa = intent.getStringExtra(CHAVE_CASA)
        casa?.let { preencheCampos(it) } ?: finish()
    }

    private fun preencheCampos(casa: String) {
        when (casa) {
            CASA_GRIFINORIA -> {
                binding.DetalheCasaImagem.setImageResource(R.drawable.gryffindor)
                binding.DetalheCasaTexto.text =
                    getString(R.string.activity_detalhe_casa_detalhesGrifinoria)
            }

            CASA_SONSERINA -> {
                binding.DetalheCasaImagem.setImageResource(R.drawable.slytherin)
                binding.DetalheCasaTexto.text =
                    getString(R.string.activity_detalhe_casa_detalhesSonserina)
            }

            CASA_LUFA_LUFA -> {
                binding.DetalheCasaImagem.setImageResource(R.drawable.hufflepuff)
                binding.DetalheCasaTexto.text =
                    getString(R.string.activity_detalhe_casa_detalhesLufaLufa)
            }

            CASA_CORVINAL -> {
                binding.DetalheCasaImagem.setImageResource(R.drawable.ravenclaw)
                binding.DetalheCasaTexto.text =
                    getString(R.string.activity_detalhe_casa_detalhesCorvinal)
            }
        }
    }

}

