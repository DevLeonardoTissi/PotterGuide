package com.example.potterguide.ui.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        val cardViewTodosOsPersonagens = binding.CardViewBotaoTodosOsPersonagens
        cardViewTodosOsPersonagens.setOnClickListener {
            texto = "Todos os personagens"
            vaiPara(PersonagensActivity::class.java) {
                putExtra(CHAVE_TELA, texto)
            }
        }

        val cardViewAlunos = binding.CardViewBotaoAlunos
        cardViewAlunos.setOnClickListener {
            texto = "Alunos"
            vaiPara(PersonagensActivity::class.java) {
                putExtra(CHAVE_TELA, texto)
            }
        }

        val cardViewFuncionarios = binding.CardViewBotaoFuncionarios
        cardViewFuncionarios.setOnClickListener {
            texto = "Funcionários"
            vaiPara(PersonagensActivity::class.java) {
                putExtra(CHAVE_TELA, texto)
            }

            val cardViewCasas = binding.CardViewBotaoCasas
            cardViewCasas.setOnClickListener {
                vaiPara(CasasActivity::class.java)
            }

            val cardViewCuriosidades = binding.CardViewBotaoCuriosidades
            cardViewCuriosidades.setOnClickListener {
                // trocaTela()
            }

            val cardViewLivros = binding.CardViewBotaoLivros
            cardViewLivros.setOnClickListener {
                // trocaTela()
            }

            val cardViewFilmes = binding.CardViewBotaoFilmes
            cardViewFilmes.setOnClickListener {
                // trocaTela()
            }

        }

    }

}

