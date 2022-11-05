package com.example.potterguide.ui.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.potterguide.R
import com.example.potterguide.databinding.ActivityMainBinding
import com.example.potterguide.extensions.vaiPara
import com.example.potterguide.repositorio.Repositorio

class MainActivity : AppCompatActivity() {

    private var identificador: String? = null

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.CardViewBotaoTodosOsPersonagens.setOnClickListener {
            identificador = getString(R.string.todosOsPersonagens)
            vaiPara(PersonagensActivity::class.java) {
                putExtra(CHAVE_TELA, identificador)
            }
        }

        binding.CardViewBotaoAlunos.setOnClickListener {
            identificador = getString((R.string.alunos))
            vaiPara(PersonagensActivity::class.java) {
                putExtra(CHAVE_TELA, identificador)
            }
        }

        binding.CardViewBotaoFuncionarios.setOnClickListener {
            identificador = getString(R.string.funcionarios)
            vaiPara(PersonagensActivity::class.java) {
                putExtra(CHAVE_TELA, identificador)
            }

        }

        binding.CardViewBotaoCasas.setOnClickListener {
            vaiPara(CasasActivity::class.java)
        }
        binding.CardViewBotaoFeiticos.setOnClickListener {
            vaiPara(FeiticosActivity::class.java)
        }

        binding.CardViewBotaoLivros.setOnClickListener {
            vaiPara(LivrosActivity::class.java)
        }

    }
}

