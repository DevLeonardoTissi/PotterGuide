package com.example.potterguide.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.potterguide.databinding.ActivityCasasBinding
import com.example.potterguide.extensions.vaiPara

class CasasActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCasasBinding.inflate(layoutInflater)
    }

    private var casa: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraCardView()
    }

    private fun configuraCardView() {
        binding.CardViewCasaGryffindor.setOnClickListener {
            casa = CHAVE_CASA_GRIFINORIA
            vaiPara(DetalhesCasaActivity::class.java) {
                putExtra(CHAVE_CASA, casa)
            }
        }

        binding.CardViewCasaHufflepuff.setOnClickListener {
            casa = CHAVE_CASA_LUFA_LUFA
            vaiPara(DetalhesCasaActivity::class.java) {
                putExtra(CHAVE_CASA, casa)
            }
        }

        binding.CardViewCasaRavenclaw.setOnClickListener {
            casa = CHAVE_CASA_CORVINAL
            vaiPara(DetalhesCasaActivity::class.java) {
                putExtra(CHAVE_CASA, casa)
            }
        }

        binding.CardViewCasaSlytherin.setOnClickListener {
            casa = CHAVE_CASA_SONSERINA
            vaiPara(DetalhesCasaActivity::class.java) {
                putExtra(CHAVE_CASA, casa)
            }
        }
    }
}