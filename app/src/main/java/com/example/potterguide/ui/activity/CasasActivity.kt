package com.example.potterguide.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.potterguide.R
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
            casa = getString(R.string.grifinoria)
            vaiPara(DetalhesCasaActivity::class.java) {
                putExtra(CHAVE_CASA, casa)
            }
        }

        binding.CardViewCasaHufflepuff.setOnClickListener {
            casa = getString(R.string.lufaLufa)
            vaiPara(DetalhesCasaActivity::class.java) {
                putExtra(CHAVE_CASA, casa)
            }
        }

        binding.CardViewCasaRavenclaw.setOnClickListener {
            casa = getString(R.string.corvinal)
            vaiPara(DetalhesCasaActivity::class.java) {
                putExtra(CHAVE_CASA, casa)
            }
        }

        binding.CardViewCasaSlytherin.setOnClickListener {
            casa = getString(R.string.sonserina)
            vaiPara(DetalhesCasaActivity::class.java) {
                putExtra(CHAVE_CASA, casa)
            }
        }
    }
}