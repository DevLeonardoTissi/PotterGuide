package com.example.potterguide.ui.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.potterguide.databinding.ActivityMainBinding
import com.example.potterguide.extensions.vaiPara

class MainActivity : AppCompatActivity() {

    private var identificador: String? = null

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.CardViewBotaoTodosOsPersonagens.setOnClickListener {
            identificador = CHAVE_TODOS_OS_PERSONAGENS
            vaiPara(PersonagensActivity::class.java) {
                putExtra(CHAVE_TELA, identificador)
            }
        }

        binding.CardViewBotaoAlunos.setOnClickListener {
            identificador = CHAVE_PERSONAGENS_ALUNOS
            vaiPara(PersonagensActivity::class.java) {
                putExtra(CHAVE_TELA, identificador)
            }
        }

        binding.CardViewBotaoFuncionarios.setOnClickListener {
            identificador = CHAVE_PERSONAGENS_fUNCIONARIOS
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

