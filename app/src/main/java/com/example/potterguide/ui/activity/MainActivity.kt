package com.example.potterguide.ui.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.potterguide.R
import com.example.potterguide.databinding.ActivityMainBinding
import com.example.potterguide.extensions.vaiPara

class MainActivity : AppCompatActivity() {

    private var texto: String? = null

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.CardViewBotaoTodosOsPersonagens.setOnClickListener {
            texto = getString(R.string.todosOsPersonagens)
            vaiPara(PersonagensActivity::class.java) {
                putExtra(CHAVE_TELA, texto)
            }
        }

        binding.CardViewBotaoAlunos.setOnClickListener {
            texto = getString((R.string.alunos))
            vaiPara(PersonagensActivity::class.java) {
                putExtra(CHAVE_TELA, texto)
            }
        }

        binding.CardViewBotaoFuncionarios.setOnClickListener {
            texto = getString(R.string.funcionarios)
            vaiPara(PersonagensActivity::class.java) {
                putExtra(CHAVE_TELA, texto)
            }

        }

        binding.CardViewBotaoCasas.setOnClickListener {
            vaiPara(CasasActivity::class.java)
        }
    }
}

